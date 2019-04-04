<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="bg-dark">

	<head>
		<meta charset="utf-8" />
		<title>登录</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	</head>

	<body>
		<section id="content" class="m-t-lg wrapper-md animated fadeInUp">
			<div class="container aside-xxl"> 
					<a class="navbar-brand block" href="#">公文管理系统</a>
					<section class="panel panel-default bg-white m-t-lg">
						<header class="panel-heading text-center">
							<strong>登录</strong>
						</header>
						
						<!-- 增删改结果提示 -->
						<jsp:include page="/common/top.jsp"></jsp:include>
						
						<form action="<%= basePath %>login" method="post" 
								class="panel-body wrapper-lg" data-validate="parsley">
							<div class="form-group">
								<label class="control-label">用户名/工号</label>
								<input name="loginname" type="text" placeholder="请输入用户名" 
									class="form-control input-lg" data-required="true"
									data-rangelength="[4,30]" data-notblank="true">
							</div>
							<div class="form-group">
								<label class="control-label">密码</label>
								<input name="password" type="password" id="inputPassword" data-required="true"
									data-rangelength="[6,15]" data-notblank="true"
									placeholder="请输入密码" class="form-control input-lg">
							</div>
							
							<div class="form-group">
								<label class="control-label">验证码</label>
								<input name="code" type="text" id="code" data-required="true"
									data-maxlength="4" data-notblank="true"
									placeholder="请输入验证码" class="form-control input-lg"/>
								<br>
								<div style="text-align: center;">
									<img id="captchaImage" src="<%= basePath %>captcha"/>
								</div>
							</div>
							
							<div style="text-align: center;">
								<button type="submit" class="btn btn-primary">登录</button>
							</div>
					</form>
				</section>
			</div>
		</section>
		<script type="text/javascript" src="<%= basePath %>js/app.v2.js"></script>
	    <script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
	    <script type="text/javascript">
		 	// 更换验证码
	        $('#captchaImage').click(function(){
	            $('#captchaImage').attr("src", "<%= basePath %>captcha?timestamp=" + (new Date()).valueOf());
	        }); 
	    </script>
	</body>
</html>