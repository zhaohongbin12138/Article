package cn.edu.nuc.article.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录拦截器
 * @author 王凯
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute("user")!=null){
			return true;
		}else{
			response.setContentType("text/html; charset=UTF-8"); 
            PrintWriter out = response.getWriter(); 				            
            out.println("<script>");     
            out.println("window.parent.location='" + request.getContextPath() + "/login.jsp'");
            out.println("</script>");
			return false;
		}
	}
	
}
