package cn.edu.nuc.article.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.nuc.article.entity.Function;
import cn.edu.nuc.article.entity.Role;
import cn.edu.nuc.article.entity.User;
import cn.edu.nuc.article.service.ArticleService;
import cn.edu.nuc.article.service.RoleService;
import cn.edu.nuc.article.service.UserService;
import cn.edu.nuc.article.util.CodeUtil;
import cn.edu.nuc.article.util.MD5Helper;

/**
 * 用户登录和加载首页Controller
 * @author 王凯
 *
 */
@Controller
public class LoginController {
	
	/**
	 * 用户业务
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 角色业务
	 */
	@Autowired
	private RoleService roleService;
	
	/**
	 * 公文Service
	 */
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		CodeUtil.drawCode(request, response);
    }
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		//移除user属性
		session.removeAttribute("user");
		
		//注销Session
		session.invalidate();
		
		//返回登录界面
		return "redirect:/login.jsp";
	}

	/**
	 * 用户登录操作
	 * @param map 保存结果集
	 * @param session 存取用户信息
	 * @param loginname 提交的登录名
	 * @param password 提交的密码
	 * @param code 提交的验证码
	 * @return
	 */
	@RequestMapping("/login")
	public String userLogin(Map<String, Object> map, HttpSession session, 
			String loginname, String password, String code) {
		
		//1.首先检查登录名、密码和验证码用户是否都填写了，如果有一样没填写就直接打回去
		
		if (!StringUtils.hasText(loginname) || !StringUtils.hasText(password)
				|| !StringUtils.hasText(code)) {
			
			//1.1 告诉用户登陆失败，这三个字段都是必填项
			map.put("msg", "登录名、密码和验证码都是必填项！");
			map.put("result", false);
			
			//1.2 直接跳回登录界面
			return "forward:/login.jsp";
		} 
		
		//2.检查验证码填写是否正确，如不正确，也要打回去
		String randomString = (String) session.getAttribute("code");
		if (!randomString.equalsIgnoreCase(code)) {
			
			//2.1 告诉用户验证码输入错误
			map.put("msg", "验证码输入错误！");
			map.put("result", false);
			
			//2.2 直接跳回登录界面
			return "forward:/login.jsp";
		}
		
		//3.检查用户输入的账号是否正确
		
		//3.1 去数据库查询用户名和密码的组合
		
		//对用户输入的密码明文进行加密，获得密文
		MD5Helper md5Helper = new MD5Helper();
		String afterEncode = md5Helper.getTwiceMD5ofString(password);
		
		//检查用户名密码（直接用密文查询）
		User user = userService.loginValidate(loginname, afterEncode);
		
		//3.2 检查登录验证是否通过，根据结果跳转
		if(user != null) { 
			//3.2.1 验证通过
			
			//3.2.1.1 如果验证通过，就要把用户信息存入Session，供以后登陆拦截检查
			session.setAttribute("user", user);
			
			//3.2.1.2 跳转到首页
			return "forward:/toIndex";
		} else {
			//3.2.2 验证不通过
			
			//3.2.2.1 提示用户登陆失败原因：用户名密码组合不正确
			map.put("msg", "登录名密码组合输入有误或登录名不存在！");
			map.put("result", false);
			
			return "forward:/login.jsp";
		}
		
	}
	
	/**
	 * 访问首页
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(Map<String, Object> map, HttpSession session) {
		
		//1.从Session中加载出用户的信息
		User user = (User) session.getAttribute("user");
		
		//2.通过用户信息找到用户的角色信息
		Role role = user.getRole();
		
		//3.通过角色信息查出角色下面的功能
		List<Function> functions = roleService.findByIdCascade(role.getRoleid()).getFunctionList();
		map.put("functionList", functions);
		
		return "index";
	}
	
	/**
	 * 访问欢迎页
	 * @param map
	 * @return
	 */
	@RequestMapping("/toWelcome")
	public String toWelcome(Map<String, Object> map, HttpSession session) {
		
		//1.从Session中取出用户信息，并得到用户id和角色id
		User user = (User) session.getAttribute("user");
		Integer userId = user.getUserid();
		Integer roleid = user.getRoleId();
		
		//2.找出要统计的4个数字
		
		//2.1 找出待处理公文数量
		Long dealcount = null;
		if (roleid == 1 || roleid == 2) {
			dealcount = articleService.getDealCount(userId);
		}
		
		//2.2 找出审核驳回公文数量
		Long failcount = articleService.getFailCount(userId);
		
		//2.3 找出待接收公文数量
		Long receivecount = articleService.getReceivedCount(userId);
		
		//2.4 找出等待审核通过公文数量
		Long waitcount = articleService.getWaitCount(userId);
		
		//3 保存查询结果
		map.put("dealcount", dealcount);
		map.put("failcount", failcount);
		map.put("receivecount", receivecount);
		map.put("waitcount", waitcount);
		
		//4.返回首页
		return "home";
	}

}
