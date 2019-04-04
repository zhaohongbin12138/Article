package cn.edu.nuc.article.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nuc.article.entity.Institution;
import cn.edu.nuc.article.entity.Role;
import cn.edu.nuc.article.entity.User;
import cn.edu.nuc.article.service.InstitutionService;
import cn.edu.nuc.article.service.RoleService;
import cn.edu.nuc.article.service.UserService;
import cn.edu.nuc.article.util.MD5Helper;
import cn.edu.nuc.article.validategroup.UserValidateGroupForInsert;
import cn.edu.nuc.article.validategroup.UserValidateGroupForPasswordModify;
import cn.edu.nuc.article.validategroup.UserValidateGroupForUpdate;
import cn.edu.nuc.article.validategroup.UserValidateGroupForUserModify;

/**
 * 用户Controller
 * @author 王凯
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	/**
	 * 用户Service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 角色Service
	 */
	@Autowired
	private RoleService roleService;
	
	/**
	 * 机构service
	 */
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * 执行批量添加操作
	 * @param map 回传参数用的map
	 * @param file 用户上传的Excel工作簿
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/addBatch")
	public String addBatch(Map<String, Object> map, MultipartFile file) throws Exception {
		
		//1.检测文件是否上传
		if (file != null && file.getSize() == 0) { //文件没上传
			map.put("result", false);
			map.put("msg", "您没有上传Excel工作簿文件！");
			return "forward:/user/toAddBatch";
		}
		
		//2.判断是否是Excel文件，如果不是就打回去
		if ("application/vnd.ms-excel".equals(file.getContentType()) == false) {
			map.put("result", false);
			map.put("msg", "您上传的不是Excel工作簿文件！");
			return "forward:/user/toAddBatch";
		}
		
		//3.解析excel保存用户信息
		if (userService.addUserBatch(file.getInputStream()) == false) {
			map.put("result", false);
			map.put("msg", "批量添加失败！");
			return "forward:/user/toAddBatch";
		}
		
		//4.如果操作成功，跳回到列表
		map.put("result", true);
		map.put("msg", "批量添加成功！");
		return "forward:/user/users";
	}
	
	/**
	 * 下载用户信息填写模板使用说明
	 * @param httpServletResponse response对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadReadFile")
	public ResponseEntity<byte[]> downloadReadFile(
				HttpServletResponse httpServletResponse) throws Exception {
		
		byte [] body = null;
		
		//获得输入流
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel/说明.docx");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename="
				+ new String("说明.docx".getBytes(), "ISO-8859-1"));
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> response = 
				new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
	
	/**
	 * 下载用户信息填写模板
	 * @param httpServletResponse response对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(
				HttpServletResponse httpServletResponse) throws Exception {
		
		byte [] body = null;
		
		//获得输入流
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel/用户模板.xls");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename="
				+ new String("用户模板.xls".getBytes(), "ISO-8859-1"));
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> response = 
				new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
	
	/**
	 * 进入批量添加界面
	 * @return
	 */
	@RequestMapping("/toAddBatch")
	public String toAddBatch() {
		
		return "user/useraddBatch";
	}
	
	/**
	 * 进入修改用户密码的界面
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/toPasswordModify")
	public String toPasswordModify(Map<String, Object> map) {
		
		return "user/usermodifypassword-self";
	}	
	
	/**
	 * 用户修改自己的密码
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(Map<String, Object> map, HttpSession session,
			//指定由哪个校验组校验
			@Validated(value=UserValidateGroupForPasswordModify.class) User user,
			BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "修改密码失败！");
		} else {
			
			//检查用户的原密码输对没有
			User sessionUser = (User) session.getAttribute("user");
			if (sessionUser.getPassword().equals(user.getOldPassword())) { //用户输对了旧密码
				
				if (sessionUser.getPassword().equals(user.getPassword())) {  //用户输入的新密码和原密码一致
					map.put("msg", "新密码不能与旧密码相同！");
				} else {
					
					//对新密码加密
					if (StringUtils.hasText(user.getPassword())) {
						String original = user.getPassword();
						MD5Helper md5Helper = new MD5Helper();
						String afterEncodepassword = md5Helper.getTwiceMD5ofString(original);
						user.setPassword(afterEncodepassword);
					}
					
					user.setUserid(sessionUser.getUserid());
					
					//保存用户信息，拿到修改操作的结果
					boolean result = userService.updateUser(user);
					map.put("result", result);
					//根据操作结果生成提示信息
					if (result == true) {  //修改成功且无重复
						//更新Session数据
						session.setAttribute("user", userService.findbyId(sessionUser.getUserid()));
						map.put("msg", "修改密码成功！");
					} else {
						
						map.put("msg", "系统出现错误，修改密码失败！");
					}
				}

			} else { //用户没输对
				map.put("msg", "您输入的旧密码不正确，修改密码失败！");
			}
		}
		
		return "forward:/user/viewSelf";
	}
	
	
	
	/**
	 * 进入修改用户个人信息的界面
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/toModifySelf")
	public String toModifySelf(Map<String, Object> map) {
		
		return "user/usermodify-self";
	}	
	
	/**
	 * 用户修改自己的信息
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/modifyself")
	public String modifyself(Map<String, Object> map, HttpSession session,
			//指定由哪个校验组校验
			@Validated(value=UserValidateGroupForUserModify.class) User user,
			BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "修改个人信息失败！");
		} else {
			
			//保存用户信息，拿到修改操作的结果
			boolean result = userService.updateUser(user);
			map.put("result", result);
			
			//根据操作结果生成提示信息
			if (result == true) {  //修改成功且无重复
				//更新Session的数据
				session.setAttribute("user", userService.findbyId(user.getUserid()));
				map.put("msg", "修改个人信息成功！");
			} else {
				
				map.put("msg", "修改个人信息失败！");
			}
		}
		
		return "forward:/user/viewSelf";
	}
	
	/**
	 * 用户查看自己信息
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/viewSelf")
	public String viewSelf(Map<String, Object> map, HttpSession session) {
		
		//加载出所有没有被禁用的机构
		Institution institution = new Institution();
		institution.setInststate(1);
		
		List<Institution> institutions = institutionService.findByKeyword(institution);
		
		map.put("institutionList", institutions);
		
		return "user/userview-self";
	}
	
	/**
	 * 管理员查看用户详细信息
	 * @param map
	 * @param userid
	 * @return
	 */
	@RequestMapping("/toView")
	public String toView(Map<String, Object> map, Integer userid) {
		
		loadUserInfo(map, userid);
		
		return "user/userview";
	}	
	
	/**
	 * 进入修改用户界面
	 * @param map
	 * @return
	 */
	@RequestMapping("/toModify")
	public String toModify(Map<String, Object> map, Integer userid) {
		
		loadUserInfo(map, userid);
		
		return "user/usermodify";
	}
	
	/**
	 * 抽取出来的方法：加载用户信息
	 * @param map
	 * @param userid
	 */
	private void loadUserInfo(Map<String, Object> map, Integer userid) {
		//1.加载出所有没有被禁用的角色供用户选择
		Role role = new Role();
		role.setRolestate(1);
		List<Role> roles = roleService.getByKeyword(role);
		
		map.put("roleList", roles);
		
		//2.加载出所有没有被禁用的机构
		Institution institution = new Institution();
		institution.setInststate(1);
		
		List<Institution> institutions = institutionService.findByKeyword(institution);
		
		map.put("institutionList", institutions);
		
		//3.加载出待修改记录
		User user = userService.findbyId(userid);
		map.put("user", user);
	}
	
	/**
	 * 修改用户信息
	 * @param map
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/modify")
	public String modify(Map<String, Object> map,
			//指定由哪个校验组校验
			@Validated(value=UserValidateGroupForUpdate.class) User user,
			BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "修改用户[" + user.getUsertruename() + "]失败！");
		} else {
			//登录名查重
			boolean hasSame = userService.hasSameName(user.getUserid(), user.getLoginname());
			
			if (hasSame == false) { //登录名名称不重复
				
				//如果设置了新密码则需要对其加密
				if (StringUtils.hasText(user.getPassword())) {
					String original = user.getPassword();
					MD5Helper md5Helper = new MD5Helper();
					String afterEncodepassword = md5Helper.getTwiceMD5ofString(original);
					user.setPassword(afterEncodepassword);
				}
				
				//保存用户信息，拿到修改操作的结果
				boolean result = userService.updateUser(user);
				map.put("result", result);
				
				//根据操作结果生成提示信息
				if (result == true) {  //修改成功且无重复
					
					map.put("msg", "修改用户[" + user.getUsertruename() + "]成功！");
				} else {
					
					map.put("msg", "修改用户[" + user.getUsertruename() + "]失败！");
				}
				
			} else {
				map.put("result", false);
				map.put("msg", "用户登录名[" + user.getUsertruename() + "]重复，修改用户失败！");
			}
		}

		return "forward:/user/users";
	}
	
	/**
	 * 进入添加单个用户界面
	 * @param map
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Map<String, Object> map) {
		
		//1.加载出所有没有被禁用的角色供用户选择
		Role role = new Role();
		role.setRolestate(1);
		List<Role> roles = roleService.getByKeyword(role);
		
		map.put("roleList", roles);
		
		//2.加载出所有没有被禁用的机构
		Institution institution = new Institution();
		institution.setInststate(1);
		
		List<Institution> institutions = institutionService.findByKeyword(institution);
		
		map.put("institutionList", institutions);
		
		return "user/useradd";
	}
	
	@RequestMapping("/add")
	public String add(Map<String, Object> map, 
			//指定由固定的校验组校验
			@Validated(value = UserValidateGroupForInsert.class) User user,
			BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "添加用户[" + user.getUsertruename() + "]失败！");
		} else {
			//登录名查重
			boolean hasSame = userService.hasSameName(null, user.getLoginname());
			
			if (hasSame == false) { //登录名不重复
				
				//如果设置了新密码则需要对其加密
				if (StringUtils.hasText(user.getPassword())) {
					String original = user.getPassword();
					MD5Helper md5Helper = new MD5Helper();
					String afterEncodepassword = md5Helper.getTwiceMD5ofString(original);
					user.setPassword(afterEncodepassword);
				}
				
				//保存用户信息，拿到添加操作的结果
				boolean result = userService.addUser(user);
				map.put("result", result);
				
				//根据操作结果生成提示信息
				if (result == true) {  //添加成功且无重复
					
					map.put("msg", "添加用户[" + user.getUsertruename() + "]成功！");
				} else {
					
					map.put("msg", "添加用户[" + user.getUsertruename() + "]失败！");
				}
				
			} else {
				map.put("result", false);
				map.put("msg", "用户登录名[" + user.getUsertruename() + "]重复，添加用户失败！");
			}
		}

		return "forward:/user/users";
	}
	
	/**
	 * 系统管理员查看用户列表
	 * @param map 携带查询结果和参数
	 * @param pageNo 目标页
	 * @param pageCount 每页显示多少记录
	 * @param keyword 查询关键字
	 * @return 
	 */
	@RequestMapping("/users")
	public String users(Map<String, Object> map,
			@RequestParam(value="pageNo", defaultValue="1", required=false) Integer pageNo,
			@RequestParam(value="pageCount", defaultValue="10", required=false) Integer pageCount,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pageNo, pageCount);
		
		// 分页查询得到结果集
		List<User> users;
		
		if (StringUtils.hasText(keyword)) {
			User user = new User();
			user.setUsertruename(keyword);
			users = userService.findByKeyword(user);
		} else {
			users = userService.findByKeyword(null);
		}
		
		
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo<User> page = new PageInfo<User>(users, 5);
		
		//保存结果集带到页面显示
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "user/userManage";
	}

}
