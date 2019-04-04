package cn.edu.nuc.article.service;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.nuc.article.dao.ArticleMapper;
import cn.edu.nuc.article.dto.State;
import cn.edu.nuc.article.dto.TreeDto;
import cn.edu.nuc.article.entity.Article;
import cn.edu.nuc.article.entity.Institution;
import cn.edu.nuc.article.entity.User;

/**
 * 公文Service
 * @author 王凯
 *
 */
@Service
public class ArticleService {

	/**
	 * 公文Mapper
	 */
	@Autowired
	private ArticleMapper articleMapper;
	
	/**
	 * 用户Service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 机构Service
	 */
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * 得到被驳回公文的数量
	 * @param userId 用户id
	 * @return
	 */
	public Long getWaitCount(Integer userId){
		return articleMapper.selectMyWaitingCount(userId);
	}
	
	/**
	 * 得到被驳回公文的数量
	 * @param userId 用户id
	 * @return
	 */
	public Long getFailCount(Integer userId){
		return articleMapper.selectMyFailCount(userId);
	}
	
	/**
	 * 得到待处理公文数量
	 * @param userId 用户id
	 * @return
	 */
	public Long getDealCount(Integer userId) {
		
		return articleMapper.selectMyDealCount(userId);
	}
	
	/**
	 * 得到待接收公文数量
	 * @param userId 用户ID
	 * @return
	 */
	public Long getReceivedCount(Integer userId) {
		return articleMapper.selectMyCountReceiver(userId);
	}
	
	/**
	 * 检查公文是否可以被访问
	 * @param articleId
	 * @param userId
	 * @return
	 */
	public boolean isArticleAvaliable(Integer articleId, Integer userId) {
		
		return articleMapper.validateAccess(userId, articleId) > 0;
	}
	
	/**
	 * 通过流程实例id加载功能信息
	 * @param ids
	 * @return
	 */
	public List<Article> getByProcessInstances(List<Task> tasks, String title, List<Integer> articlestates) {
		
		return articleMapper.selectByProcessInstances(tasks, title, articlestates);
	}
	
	/**
	 * 按id加载公文记录
	 * @param articleId 公文Id
	 * @return
	 */
	public Article getArticleById(Integer articleId, Integer userId) {
		
		return articleMapper.selectOne(articleId, userId);
	}
	
	/**
	 * 查询出与我相关的公文
	 * @param article
	 * @return
	 */
	public List<Article> getMyArticles(Article article) {
		
		return articleMapper.selectMyReceiveList(article);
	}
	
	/**
	 * 找出与我相关的公文
	 * @param article
	 * @return
	 */
	public List<Article> getMyAuditList(Article article) {
		
		return articleMapper.selectMyAuditList(article);
	}
	
	/**
	 * 找出与我相关的公文
	 * @param article
	 * @return
	 */
	public List<Article> getMyArticleList(Article article) {
		
		return articleMapper.selectMyList(article);
	}
	
	/**
	 * 测试公文标题是否重复
	 * @param title
	 * @return true 不重复。 false 重复
	 */
	public boolean validateTitle(String title, Integer articleid) {
		
		return articleMapper.validateTitle(title, articleid) == 0;
	}
	
	/**
	 * 更新公文信息
	 * @param article
	 * @return
	 */
	public boolean modifyArticle(Article article) {
		
		return articleMapper.updateByPrimaryKeySelective(article) > 0;
	}
	
	/**
	 * 添加公文
	 * @return
	 */
	public boolean addArticle(Article article) {
		
		return articleMapper.insertSelective(article) > 0;
	}
	
	/**
	 * 获得树的DTO对象的JSON字符串
	 * @return
	 * @throws Exception 
	 */
	public TreeDto getTreeDTOJSON(Integer articleId, Integer userId) throws Exception {
		
		//1.加载出所有的未被禁用的机构
		
		//1.1 设置查询条件，要求机构的状态必须是正常，即不可以是禁用或者被合并
		Institution institution1 = new Institution();
		institution1.setInststate(1);  //1代表正常
		
		//1.2 使用查询条件拿到结果集
		List<Institution> institutions = institutionService.findByKeyword(institution1);
		
		//2.加载出未被禁用的用户
		List<User> users = userService.findValidUser();
		
		//3.如果有公文id，还要查询出公文信息（修改公文时用）
		Article article = null;
		if (articleId != null) {
			article = articleMapper.selectOne(articleId, userId);
		}
		
		return getTree(institutions, users, article);
	}

	/**
	 * 根据机构和用户信息装出联系人树对象
	 * @param institutions 机构信息
	 * @param users 用户信息
	 * @return 联系人树
	 */
	private TreeDto getTree(List<Institution> institutions, List<User> users,
			Article article) {

		//标志位
		boolean isFirstLevelSelected = false;
		
		//1 先构造出树的根
		TreeDto root = new TreeDto();
		root.setText("所有联系人");
		
		//2 遍历每一个机构
		for (Institution institution : institutions) {
			
			//3.2 为当前机构构建出一个一级子节点
			TreeDto firstLevel = new TreeDto();
			firstLevel.setText(institution.getInstname());  //节点名称就是机构名称
			
			boolean isSecondLevelSelected = false;
			
			//3.3 遍历每一个用户
			for (User user : users) {
				
				//3.4 如果这个用户属于当前机构
				if (user.getInstId() == institution.getInstid()) {
					
					//3.5 为当前用户创建子节点
					TreeDto secondLevel = new TreeDto();
					secondLevel.setText(user.getUsertruename()); //节点名称是用户真实姓名
					secondLevel.setId(user.getUserid()); //用户节点的id就是用户id，只有用户节点才有id
					
					//判断是否需要设置选中
					if (article != null) {
						List<User> receivers = article.getReceivers();
						for (User receiver : receivers) {
							if (user.getUserid() == receiver.getUserid()) {
								State state = new State();
								state.setChecked(true);
								secondLevel.setState(state);
								isSecondLevelSelected = true;
							}
						}
					}
					
					//3.6把用户节点加到机构节点下面
					firstLevel.addNode(secondLevel);

				}//if
			}//for user
			
			//3.7判断是否勾选
			if (isSecondLevelSelected == true) {
				isFirstLevelSelected = true;
				State state = new State();
				state.setChecked(true);
				firstLevel.setState(state);
			}

			//3.8 把机构节点加入到根节点下面
			root.addNode(firstLevel);
		}
		
		if (isFirstLevelSelected == true) {
			 State state = new State();
			 state.setChecked(true);
			 root.setState(state);
		}
		
		return root;
	}
}
