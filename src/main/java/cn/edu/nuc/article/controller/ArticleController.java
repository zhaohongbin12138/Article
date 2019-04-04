package cn.edu.nuc.article.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.nuc.article.dto.TreeDto;
import cn.edu.nuc.article.entity.Article;
import cn.edu.nuc.article.entity.Attachment;
import cn.edu.nuc.article.entity.AuditMessage;
import cn.edu.nuc.article.entity.Log;
import cn.edu.nuc.article.entity.User;
import cn.edu.nuc.article.service.ArticleService;
import cn.edu.nuc.article.service.AttachmentService;
import cn.edu.nuc.article.service.AuditMessageService;
import cn.edu.nuc.article.service.FileService;
import cn.edu.nuc.article.service.LogService;
import cn.edu.nuc.article.service.ReceiveService;
import cn.edu.nuc.article.service.UserService;
import cn.edu.nuc.article.util.IpUtil;

/**
 * 公文Controller
 * @author 王凯
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	/**
	 * 公文Service
	 */
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 用户Service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 文件服务
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * 公文接收Service
	 */
	@Autowired
	private ReceiveService receiveService;
	
	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * 公文审核信息Service
	 */
	@Autowired
	private AuditMessageService auditMessageService;
	
	/**
	 * 日志Service
	 */
	@Autowired
	private LogService logService;
	
	/**
	 * Activiti运行时Service，用于启动流程
	 */
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * Activiti任务Service，查询待办任务和完成任务
	 */
	@Autowired
	private TaskService taskService;
	
	/**
	 * 公文修改（撰稿）
	 * @return
	 */
	@RequestMapping("/modifyArticle")
	@Transactional
	public String modifyArticle(Map<String, Object> map, HttpSession session ,
			String title, Integer articleId, Integer[] received, Integer auditor, 
			String taskId,
			@RequestParam(name= "doc", required = false) MultipartFile doc,
			@RequestParam(name= "attachment", required = false) MultipartFile[] attachment) {
		
		try {
			//1.首先检查必填项是否都填写正确
			
			if (validateForm(map, articleId, title, received, auditor, doc) == false) {
				return "forward:/article/toModify";
			}
			
			//2.设置公文相关参数并新增公文
			Article article = new Article();
			article.setArticleid(articleId);
			article.setArticlestate(0); //0表示审核中
			article.setTitle(title);
			
			//撰稿人、审稿人、发布时间在公文撰稿时已经设置过，不需要也不能改
			
			//判断操作结果是否成功
			if (!articleService.modifyArticle(article)) {
				map.put("msg", "系统出现错误，在修改公文信息时操作失败！");
				map.put("result", false);
				return "forward:/article/toModify";
			}
			
			//拿到修改后的公文信息
			User user = (User) session.getAttribute("user");
			article = articleService.getArticleById(articleId, user.getUserid());

			//3 删除旧的接收关系，再添加信息
			
			//3.1 删除旧的接收关系
			if (receiveService.deleteReceive(articleId)) {
				
				//3.2 添加新的接收人信息
				for (Integer receiverId : received) {
					
					if (!receiveService.addReceive(articleId, receiverId)) {
						map.put("msg", "系统出现错误，在修改公文接收人信息时操作失败！");
						map.put("result", false);
						return "forward:/article/toModify";
					}
				}
				
			} else {
				map.put("msg", "系统出现错误，在修改公文接收人信息时操作失败！");
				map.put("result", false);
				return "forward:/article/toModify";
			}

			//4. 看一下公文原文是否需要更新，如果需要则更新之
			if (doc != null && doc.getSize() != 0) {
				
				//4.1 删掉旧的公文原文信息
				Attachment target = null;
				for (Attachment attachment2 : article.getAttachments()) {
					if (attachment2.getAttachtype() == 0) { //正文
						target = attachment2;
					}
				}
				
				boolean result = attachmentService.deleteAttachment(target.getAttachmentid());
				
				if (result == true) {
					
					//4.2 调用FileService，使用JackRabbit保存新的公文电子文档
					String fileId = fileService.save(doc.getInputStream());
					
					//4.3 拼装出公文原文的附件信息
					Attachment articleDocument = new Attachment();
					articleDocument.setArticleId(article.getArticleid());
					articleDocument.setAttachtype(0); //0表示正文
					articleDocument.setFilename(doc.getOriginalFilename());
					articleDocument.setMimetype(doc.getContentType());
					articleDocument.setUploadtime(new Date());
					articleDocument.setFilesize(doc.getSize());
					articleDocument.setFileid(fileId); //JackRabbit返回的FileId
					
					//4.4 添加信息到附件表
					if (!attachmentService.addAttachment(articleDocument)) {
						map.put("msg", "系统出现错误，在添加公文附件时操作失败！");
						map.put("result", false);
						return "forward:/article/toModify";
					}
				} else {
					map.put("msg", "系统出现错误，在删除公文旧附件时操作失败！");
					map.put("result", false);
					return "forward:/article/toModify";
				}
			}

			//5.如果用户上传了附件，就要做对应更新
			if (attachment != null && attachment.length != 0 && attachment[0].getSize() != 0) {
				
				//5.1 删掉公文旧附件
				for (Attachment attachment2 : article.getAttachments()) {
					if (attachment2.getAttachtype() != 0) { //附件
						attachmentService.deleteAttachment(attachment2.getAttachmentid());
					}
				}
				
				//5.2 上传新附件
				for (MultipartFile attachFile : attachment) {
					
					//5.2.1 调用FileService，使用JackRabbit保存公文附件
					String attfileId = fileService.save(attachFile.getInputStream());
					
					//5.2.2 拼装附件信息
					Attachment attch = new Attachment();
					attch.setArticleId(article.getArticleid());
					attch.setAttachtype(1); //1表示附件
					attch.setFileid(attfileId);
					attch.setFilename(attachFile.getOriginalFilename());
					attch.setFilesize(attachFile.getSize());
					attch.setMimetype(attachFile.getContentType());
					attch.setUploadtime(new Date());
					
					//5.2.3 添加信息到附件表
					if (!attachmentService.addAttachment(attch)) {
						map.put("msg", "系统出现错误，在修改公文附件时操作失败！");
						map.put("result", false);
						return "forward:/article/toModify";
					}
					
				}
			}
			
			//6.完成公文修改的待办任务，使其转向公文审核任务
			
			//6.1 完成选择，使其转向修改公文任务
			Map<String, Object> variables = new HashMap<>();
			variables.put("decision", true);
			
			taskService.complete(taskId, variables);
			
			//6.2 根据流程实例和办理人查询待办，得到修改公文任务的TaskId,然后完成该任务
			Task task = taskService.createTaskQuery()
					.taskAssignee(user.getUserid().toString())
					.processInstanceId(article.getProcessinstanceId())
					.singleResult();
			
			taskService.complete(task.getId());

			//7.如果以上操作都没有问题，那就是成功了
			map.put("msg", "公文[" + article.getTitle() + "]上传成功，请耐心等待审核！");
			map.put("result", true);
			
			//返回等待审核结果界面
			return "forward:/article/toAduitResult";
		} catch (Exception e) {
			System.out.println("公文修改出错！");
			e.printStackTrace();
			
			map.put("msg", "系统出现严重错误，公文修改操作失败！");
			map.put("result", false);
			return "forward:/article/toModfiy";
		}
	}
	
	/**
	 * 进入公文修改界面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toModfiy")
	public String toModify(HttpSession session, Map<String, Object> map,
			Integer articleId, String taskId) throws Exception {
		
		//加载出该用户所在机构所有审稿人姓名
		
		//1 从Session中取出User
		User user = (User) session.getAttribute("user");
		
		//2 通过用户得到其所在机构的id
		Integer instid = user.getInstId();
		
		//3 得到该机构所有未被禁用的审核人员的信息
		List<User> auditors = userService.findValidAuditor(instid);
		
		//4 得到公文信息
		Article article = articleService.getArticleById(articleId, user.getUserid());

		//5 保存为备选项
		map.put("auditors", auditors);
		map.put("article", article);
		map.put("taskId", taskId);
		
		return "article/articlemodify";
	}
	
	/**
	 * 通过Ajax方式获得联系人树(修改)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getTreeModify")
	public List<TreeDto> getTreeModify(HttpSession session, Integer articleId)
			throws Exception {
		
		User user = (User) session.getAttribute("user");
		
		List<TreeDto> dtos = new ArrayList<>();
		dtos.add(articleService.getTreeDTOJSON(articleId, user.getUserid()));
		return dtos;
	}
	
	/**
	 * 用户选择删除公文
	 * @param map
	 * @return
	 */
	@RequestMapping("/deleteArticle")
	public String deleteArticle(Map<String, Object> map, Integer articleId, String taskId) {
		
		//设置公文状态为被删除
		Article article = new Article();
		article.setArticleid(articleId);
		article.setArticlestate(4);
		
		if (articleService.modifyArticle(article)) {
			
			Map<String, Object> variables = new HashMap<>();
			variables.put("decision", false);
			
			taskService.complete(taskId, variables);
			
			map.put("msg", "删除公文成功");
			map.put("result", true);
		} else {
			map.put("msg", "删除公文失败");
			map.put("result", false);
		}

		return "forward:/article/toAduitResult";
	}
	
	/**
	 * 按id加载公文接收信息
	 * @return
	 */
	@RequestMapping("/findByIdHistory")
	public String findByIdHistory(Map<String, Object> map, HttpSession session,
				HttpServletRequest request, Integer articleId) {
		
		//1.从Session中获得User，取得其UserId
		User user = (User) session.getAttribute("user");
		Integer userId = user.getUserid();
		
		//2.向日志表插入一条日志记录
		Log log = new Log();
		log.setBussinessId(articleId);
		log.setIpaddress(IpUtil.getRequestRealIp(request)); //获取ip地址
		log.setOperatorId(userId);
		log.setOptname("查看公文");
		log.setOpttime(new Date());
		logService.addLog(log);
		
		//3.检查访问权限
		if (articleService.isArticleAvaliable(articleId, userId) == true) {
			Article article = articleService.getArticleById(articleId, userId); 
			
			//4.保存查询结果
			map.put("article", article);
			return "article/articleDetail";
		} else {
			map.put("msg", "您没有查看公文的权限！");
			map.put("result", false);
			return "forward:/article/getMyHistoryList";
		}
	}
	
	/**
	 * 查询接收公文信息
	 * @param map
	 * @param session
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/getMyHistoryList")
	public String getMyHistoryList(Map<String, Object> map, HttpSession session,
			@RequestParam(value="keyword", required=false) String keyword) {

		//找出当前登录用户
		User user = (User) session.getAttribute("user");
		
		//设置查询条件
		Article article = new Article();
		article.setUserId(user.getUserid());
		
		if (StringUtils.hasText(keyword)) { //用户指定了查询关键字
			article.setTitle(keyword);
		}

		List<Article> articles = articleService.getMyArticleList(article);

		//保存结果集带到页面显示
		map.put("articles", articles);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "article/articleHistoryManage";
	}
	
	/**
	 * 按id加载公文接收信息
	 * @return
	 */
	@RequestMapping("/findByIdReceive")
	public String findByIdReceive(Map<String, Object> map, HttpSession session,
				HttpServletRequest request, Integer articleId) {
		
		//1.从Session中获得User，取得其UserId
		User user = (User) session.getAttribute("user");
		Integer userId = user.getUserid();
		
		//2.向日志表插入一条日志记录
		Log log = new Log();
		log.setBussinessId(articleId);
		log.setIpaddress(IpUtil.getRequestRealIp(request)); //获取ip地址
		log.setOperatorId(userId);
		log.setOptname("查看公文");
		log.setOpttime(new Date());
		logService.addLog(log);
		
		//3.检查访问权限
		if (articleService.isArticleAvaliable(articleId, userId) == true) {
			Article article = articleService.getArticleById(articleId, userId); 
			
			//4.保存查询结果
			map.put("article", article);
			return "article/articleDetail";
		} else {
			map.put("msg", "您没有查看公文的权限！");
			map.put("result", false);
			return "forward:/article/getMyReceiveList";
		}
	}
	
	/**
	 * 查询接收公文信息
	 * @param map
	 * @param session
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/getMyReceiveList")
	public String getMyReceiveList(Map<String, Object> map, HttpSession session,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		//找出当前登录用户
		User user = (User) session.getAttribute("user");
		
		//设置查询条件
		Article article = new Article();
		article.setArticlestate(3);
		article.setUserId(user.getUserid());
		
		if (StringUtils.hasText(keyword)) { //用户指定了查询关键字
			article.setTitle(keyword);
		}
		
		List<Article> articles = articleService.getMyArticles(article);
		
		//保存结果集带到页面显示
		map.put("articles", articles);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "article/articleReceiveManage";
	}
	
	/**
	 * 提交公文审核结果
	 * @param map
	 * @param taskId
	 * @param result
	 * @param articleId
	 * @param auditmessage
	 * @return
	 */
	@RequestMapping("/submitAuditResult")
	public String submitAuditResult(Map<String, Object> map, String taskId,
			Integer result, Integer articleId, String auditmessage){
		
		AuditMessage auditMessage = new AuditMessage();
		auditMessage.setAuditdate(new Date());
		auditMessage.setAuditmessage(auditmessage);
		auditMessage.setAuditresult(result);
		auditMessage.setArticleId(articleId);
		
		//先添加审核信息
		if (auditMessageService.addAuditMessage(auditMessage)) {
			
			Map<String, Object> variables = new HashMap<>();
			
			if (result == 1) { //审核通过
				variables.put("auditresult", true);
				
				Article article = new Article();
				article.setArticleid(articleId);
				article.setArticlestate(3); //3表示审核通过且发布
				articleService.modifyArticle(article); //更新数据库字段
			} else {
				variables.put("auditresult", false);
				
				Article article = new Article();
				article.setArticleid(articleId);
				article.setArticlestate(2); //2表示审核驳回
				articleService.modifyArticle(article); //更新数据库字段
			}
			
			//使用流程变量完成待办任务
			taskService.complete(taskId, variables);
			
			map.put("msg", "提交审核结果成功！");
			map.put("result", true);
			
		} else {
			map.put("msg", "提交审核结果失败！");
			map.put("result", false);
		}
		
		return "redirect:/article/toAduit";
	}
	
	/**
	 * 下载公文或附件
	 * @param session
	 * @param filename
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> downloadArticle(HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpServletResponse,
			Integer attachmentid, String fileId) throws Exception {
		
		byte [] body = null;
		
		Attachment attachment = new Attachment();
		attachment.setAttachmentid(attachmentid);
		attachment = attachmentService.findByKeyword(attachment).get(0);
		
		User user = (User) session.getAttribute("user");
		boolean result = articleService.isArticleAvaliable(
				attachment.getArticleId(), user.getUserid());
		
		if (result == true) {
			
			//如果下载的是公文原文而不是附件，则向日志表插入一条日志记录
			if (attachment.getAttachtype() == 0) {
				Log log = new Log();
				log.setBussinessId(attachment.getArticleId());
				log.setIpaddress(IpUtil.getRequestRealIp(request)); //获取ip地址
				log.setOperatorId(user.getUserid());
				log.setOptname("下载公文");
				log.setOpttime(new Date());
				logService.addLog(log);
			}
			
			//通过JackRabbit找到文件，获得输入流
			InputStream in = fileService.getByFileId(fileId);
			body = new byte[in.available()];
			in.read(body);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment;filename=" 
					+ new String(attachment.getFilename().getBytes(), "ISO-8859-1"));
			HttpStatus statusCode = HttpStatus.OK;
			ResponseEntity<byte[]> response = 
					new ResponseEntity<byte[]>(body, headers, statusCode);
			return response;
		}
		
		return null;
	}
	
	/**
	 * 按id加载公文信息
	 * @return
	 */
	@RequestMapping("/toAuditPage")
	public String toAuditPage(Map<String, Object> map, 
			HttpSession session, Integer articleId) {
		
		//1.从Session中获得User，取得其UserId
		User user = (User) session.getAttribute("user");
		Integer userId = user.getUserid();
		
		//2. 读取当前用户的待办任务，包括直接分配给当前人或已经签收的任务
		List<Task> doingTask = taskService.createTaskQuery()
				.taskAssignee(user.getUserid().toString()).list();
		
		//2.检查访问权限
		if (articleService.isArticleAvaliable(articleId, userId) == true) {
			
			//3.查询公文信息
			Article article = articleService.getArticleById(articleId, userId); 
			
			//4.根据公文的流程实例id找到当前任务
			Task task = null;
			for (Task task1 : doingTask) {
				if (article.getProcessinstanceId().equals(task1.getProcessInstanceId())) {
					task = task1;
				}
			}
			
			//3.保存查询结果
			map.put("article", article);
			map.put("task", task);
			return "article/articleAuditDetail";
		} else {
			map.put("msg", "您没有查看公文的权限！");
			map.put("result", false);
			return "forward:/article/toAduit";
		}
	}
	
	/**
	 * 按id加载公文审核结果信息
	 * @return
	 */
	@RequestMapping("/findById")
	public String findById(Map<String, Object> map, HttpSession session,
				HttpServletRequest request, Integer articleId) {
		
		//1.从Session中获得User，取得其UserId
		User user = (User) session.getAttribute("user");
		Integer userId = user.getUserid();
		
		//2.检查访问权限
		if (articleService.isArticleAvaliable(articleId, userId) == true) {
			
			//3.向日志表插入一条日志记录
			Log log = new Log();
			log.setBussinessId(articleId);
			log.setIpaddress(IpUtil.getRequestRealIp(request)); //获取ip地址
			log.setOperatorId(userId);
			log.setOptname("查看公文");
			log.setOpttime(new Date());
			logService.addLog(log);
			
			Article article = articleService.getArticleById(articleId, userId); 
			
			//4.保存查询结果
			map.put("article", article);
			return "article/articleDetail";
		} else {
			map.put("msg", "您没有查看公文的权限！");
			map.put("result", false);
			return "forward:/article/toAduitResult";
		}
	}
	
	/**
	 * 进入审核界面
	 * @param map
	 * @param pageNo
	 * @param pageCount
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/toAduit")
	public String toAduit(Map<String, Object> map, HttpSession session,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 查询得到结果集
		List<Article> articles;
		
		User user = (User) session.getAttribute("user");
		
		//读取当前用户的待办任务，包括直接分配给当前人或已经签收的任务
		List<Task> doingTask = taskService.createTaskQuery()
				.taskAssignee(user.getUserid().toString()).list();

		if (doingTask != null && doingTask.size() != 0) { //有该用户的待办任务
			
			if (StringUtils.hasText(keyword)) { //用户指定了查询关键字
				articles = articleService.getByProcessInstances(doingTask, keyword, Arrays.asList(0));
			} else { //用户没指定查询关键字
				articles = articleService.getByProcessInstances(doingTask, null, Arrays.asList(0));
			}
		} else { //没有待办任务
			articles = new ArrayList<>();
		}
		
		//保存结果集带到页面显示
		map.put("articles", articles);
		map.put("tasks", doingTask);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "article/articleAuditManage";
	}
	
	/**
	 * 进入审核结果
	 * @param map
	 * @param pageNo
	 * @param pageCount
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/toAduitResult")
	public String toAduitResult(Map<String, Object> map, HttpSession session,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 查询得到结果集
		List<Article> articles;
		
		User user = (User) session.getAttribute("user");
		
		//读取当前用户的待办任务，包括直接分配给当前人或已经签收的任务
		List<Task> doingTask = taskService.createTaskQuery()
				.taskAssignee(user.getUserid().toString()).list();
		
		if (doingTask != null && doingTask.size() != 0) { //有该用户的待办任务
			
			if (StringUtils.hasText(keyword)) { //用户指定了查询关键字
				articles = articleService.getByProcessInstances(doingTask, keyword, Arrays.asList(1, 2));
			} else { //用户没指定查询关键字
				articles = articleService.getByProcessInstances(doingTask, null, Arrays.asList(1, 2));
			}
		} else { //没有待办任务
			articles = new ArrayList<>();
		}
		
		//为每一篇公文加入对应的Task
		for (Task task : doingTask) {
			for (Article article : articles) {
				if (task.getProcessInstanceId().equals(article.getProcessinstanceId())) {
					article.setTask(task);
				}
			}
		}
		
		//查询出正在审核中的公文
		Article article = new Article();
		article.setTitle(keyword);
		article.setUserId(user.getUserid());
		List<Article> auditing = articleService.getMyAuditList(article);
		articles.addAll(auditing); //合并两个集合
		
		//保存结果集带到页面显示
		map.put("articles", articles);
		map.put("tasks", doingTask);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "article/articleAuditResultManage";
	}
	
	/**
	 * 公文上传（撰稿）
	 * @return
	 */
	@RequestMapping("/addArticle")
	@Transactional
	public String addArticle(Map<String, Object> map, HttpSession session ,
			String title, 
			Integer[] received, 
			Integer auditor, 
			MultipartFile doc,
			@RequestParam(name= "attachment", required = false) MultipartFile[] attachment) {
		
		try {
			//1.首先检查必填项是否都填写正确
			
			if (validateForm(map, null, title, received, auditor, doc) == false) {
				return "forward:/article/toAdd";
			}
			
			//2.设置公文相关参数并新增公文
			Article article = new Article();
			article.setArticlestate(0); //0表示审核中
			article.setTitle(title);
			
			//撰稿人就是当前用户
			User user = (User)session.getAttribute("user");
			article.setCopywriterId(user.getUserid());
			article.setInstId(user.getInstId());
			
			article.setAuditorId(auditor);
			article.setPublishtime(new Date());
			
			//判断操作结果是否成功
			if (!articleService.addArticle(article)) {
				map.put("msg", "系统出现错误，在添加公文信息时操作失败！");
				map.put("result", false);
				return "forward:/article/toAdd";
			}
			
			//3.启动Activiti工作流流程，拿到流程实例id
			
			//3.1 设置流程变量，指定审核人(为防止用户姓名重复，使用用户id)
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditorId", auditor);              //指定公文审核的办理人Id
			variables.put("copywriterId", user.getUserid());  //指定撰稿人Id
			
			//3.2 按流程实例id启动流程
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					"articleProcess", article.getArticleid().toString() , variables);
			
			//3.3 得到流程实例id
			String procInstanceKey = processInstance.getId();
			
			//3.4 更新公文信息，设置流程id
			article.setProcessinstanceId(procInstanceKey);
			if (!articleService.modifyArticle(article)) {
				map.put("msg", "系统出现错误，在添加公文信息时操作失败！");
				map.put("result", false);
				return "forward:/article/toAdd";
			}
			
			//4 利用公文id继续去添加其他信息
			
			//4.1 添加接收关系
			for (Integer receiverId : received) {
				
				if (!receiveService.addReceive(article.getArticleid(), receiverId)) {
					map.put("msg", "系统出现错误，在添加公文接收信息时操作失败！");
					map.put("result", false);
					return "forward:/article/toAdd";
				}
			}

			//4.2 调用FileService，使用JackRabbit保存公文电子文档
			String fileId = fileService.save(doc.getInputStream());
			
			//4.3 拼装出公文原文的附件信息
			Attachment articleDocument = new Attachment();
			articleDocument.setArticleId(article.getArticleid());
			articleDocument.setAttachtype(0); //0表示正文
			articleDocument.setFilename(doc.getOriginalFilename());
			articleDocument.setMimetype(doc.getContentType());
			articleDocument.setUploadtime(new Date());
			articleDocument.setFilesize(doc.getSize());
			articleDocument.setFileid(fileId); //JackRabbit返回的FileId
			
			//4.4 添加信息到附件表
			if (!attachmentService.addAttachment(articleDocument)) {
				map.put("msg", "系统出现错误，在添加公文附件时操作失败！");
				map.put("result", false);
				return "forward:/article/toAdd";
			}
			
			//5.如果用户上传了附件，就要把附件也保存下来
			
			for (MultipartFile attachFile : attachment) {
				
				//5.1 调用FileService，使用JackRabbit保存公文附件
				String attfileId = fileService.save(attachFile.getInputStream());
				
				//5.2 拼装附件信息
				Attachment attch = new Attachment();
				attch.setArticleId(article.getArticleid());
				attch.setAttachtype(1); //1表示附件
				attch.setFileid(attfileId);
				attch.setFilename(attachFile.getOriginalFilename());
				attch.setFilesize(attachFile.getSize());
				attch.setMimetype(attachFile.getContentType());
				attch.setUploadtime(new Date());
				
				//5.3 添加信息到附件表
				if (!attachmentService.addAttachment(attch)) {
					map.put("msg", "系统出现错误，在添加公文附件时操作失败！");
					map.put("result", false);
					return "forward:/article/toAdd";
				}
				
			}

			//6.如果以上操作都没有问题，那就是成功了
			map.put("msg", "公文[" + article.getTitle() + "]上传成功，请耐心等待审核！");
			map.put("result", true);
			
			//返回等待审核结果界面
			return "forward:/article/toAduitResult";
		} catch (Exception e) {
			System.out.println("公文撰稿出错！");
			e.printStackTrace();
			
			map.put("msg", "系统出现严重错误，公文撰稿操作失败！");
			map.put("result", false);
			return "forward:/article/toAdd";
		}
	}

	/**
	 * 检查公文撰稿表单是否填写正确
	 * @param map
	 * @param title
	 * @param received
	 * @param auditor
	 * @param doc
	 * @return
	 */
	private boolean validateForm(Map<String, Object> map, Integer articleid, 
			String title, Integer[] received, Integer auditor, MultipartFile doc) {
		
		//1.1检查公文标题是否符合规定格式
		if (StringUtils.hasText(title) == false 
				|| Pattern.matches("^\\S{2,150}$", title) == false) {
			map.put("msg", "您填写的公文标题不合法，上传失败！公文标题不能为空，并且长度为2~150。");
			map.put("result", false);
			return false;
		}
		
		//1.2 检查接收人id是否填写
		if (received == null || received.length == 0) {
			map.put("msg", "您还没有选择接收人，上传失败！");
			map.put("result", false);
			return false;
		}
		
		//1.3 检查审核人是否填写
		if (articleid == null && auditor == null) {
			map.put("msg", "您还没有选择审核人，上传失败！");
			map.put("result", false);
			return false;
		}
		
		//1.4 检查公文电子文档是否上传
		if (articleid == null && doc == null) {
			map.put("msg", "您还没有上传公文电子文档，上传失败！");
			map.put("result", false);
			return false;
		}
		
		//1.5 检查公文名称是否重复，如果重复则不允许其通过
		if (articleService.validateTitle(title, articleid) == false) {
			map.put("msg", "您填写的公文标题与已有标题重复，上传失败！");
			map.put("result", false);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 进入公文撰稿界面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpSession session, Map<String, Object> map) throws Exception {
		
		//加载出该用户所在机构所有审稿人姓名
		
		//1 从Session中取出User
		User user = (User) session.getAttribute("user");
		
		//2 通过用户得到其所在机构的id
		Integer instid = user.getInstId();
		
		//3 得到该机构所有未被禁用的审核人员的信息
		List<User> auditors = userService.findValidAuditor(instid);
		
		//4 保存为备选项
		map.put("auditors", auditors);
		
		return "article/articleupload";
	}
	
	/**
	 * 通过Ajax方式获得联系人树
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getTree")
	public List<TreeDto> getTree() throws Exception {
		
		List<TreeDto> dtos = new ArrayList<>();
		dtos.add(articleService.getTreeDTOJSON(null, null));
		return dtos;
	}
	
}
