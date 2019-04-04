<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>公文管理系统</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	</head>

	<body>
		<section class="vbox">
			<!-- 最上面的导航 -->
			<header class="bg-dark dk header navbar navbar-fixed-top-xs">
				<!-- 左上角Logo -->
				<div class="navbar-header aside-md">
					<a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen" 
						data-target="#nav"> 
						<i class="fa fa-bars"></i> 
					</a>
					<a href="#" class="navbar-brand" data-toggle="fullscreen">
						<img src="images/logo.png" class="m-r-sm">
						公文管理系统
					</a>
					<a class="btn btn-link visible-xs" data-toggle="dropdown" 
						data-target=".nav-user">
						<i class="fa fa-cog"></i>
					</a>
				</div>
				
				<!-- 右上角的个人信息 -->
				<ul class="nav navbar-nav navbar-right hidden-xs nav-user">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<span class="thumb-sm avatar pull-left">
								<img src="images/avatar.jpg">
							</span>
							${sessionScope.user.usertruename}（${sessionScope.user.role.rolename}）
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu animated fadeInRight">
							<span class="arrow top"></span>
							<li>
								<a href="<%= basePath %>user/viewSelf" target="main">查看个人信息</a>
							</li>
							<li>
								<a href="<%= basePath %>user/toModifySelf" target="main">修改个人信息</a>
							</li>
							<li>
								<a href="<%= basePath %>user/toPasswordModify" target="main">修改密码</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="<%= basePath %>logout">注销</a>
							</li>
						</ul>
					</li>
				</ul>
			</header>
			
			<!-- 中间的业务数据  -->
			<section>
				<section class="hbox stretch">
					<!-- .aside -->
					<aside class="bg-dark lter aside-md hidden-print" id="nav">
						<!-- 想要更换菜单栏皮肤为白色的方法：设置aside的class为bg-light........ -->
						<section class="vbox">
							<!-- 功能菜单条 -->
							<section class="w-f scrollable">
								<div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">
									<nav class="nav-primary hidden-xs">
										<!-- 功能菜单 -->
										<ul class="nav">
											<!-- 首页，默认显示这一页 -->
											<li class="active">
												<a href="<%= basePath %>toWelcome" class="active" target="main">
													<i class="fa fa-dashboard icon">
														<b class="bg-danger"></b>
													</i> 
													<span>首页</span>
												</a>
											</li>
											
											<!-- 加载功能列表 -->
											<c:forEach items="${functionList}" var="parent">
												<!-- 如果父功能被禁用，则下方的子功能无论是否被禁用都不显示 -->
												<c:if test="${parent.funstate == 1 && parent.funpid == -1}">
													<!-- 父功能（一级菜单） -->
													<li>
														<!-- 父菜单：取消默认的点击事件行为 -->
														<a href="#" onclick="return false;"> 
															<i class="fa fa-angle-right icon"> 
																<c:choose>
																	<c:when test="${parent.funid % 5 == 1}"><b class="bg-primary"></b></c:when>
																	<c:when test="${parent.funid % 5 == 2}"><b class="bg-warning"></b></c:when>
																	<c:when test="${parent.funid % 5 == 3}"><b class="bg-success"></b></c:when>
																	<c:when test="${parent.funid % 5 == 4}"><b class="bg-dark"></b></c:when>
																	<c:when test="${parent.funid % 5 == 0}"><b class="bg-info"></b></c:when>
																</c:choose>
															</i> 
															<span class="pull-right"> 
																<i class="fa fa-angle-down text"></i> 
																<i class="fa fa-angle-up text-active"></i> 
															</span> 
															<span>${parent.funname}</span> 
														</a>
														
														<!-- 子菜单 -->
														<ul class="nav lt">
															<c:forEach items="${functionList}" var="child">
																<!-- 若某个父功能下面的子功能被禁用，则不加载此功能 -->
																<c:if test="${child.funstate == 1 && child.funpid == parent.funid}">
																	<!-- 加载子菜单 -->
																	<li>
																		<a href="<%= basePath %>${child.funurl}" target="main"> 
																			<i class="fa fa-angle-right"></i> 
																			<span>${child.funname}</span> 
																		</a>
																	</li>
																</c:if>
															</c:forEach>
														</ul>
													</li>
												</c:if>
												
											</c:forEach>
											
										</ul>
									</nav>
									<!-- / nav -->
								</div>
							</section>
							
							<!-- 功能菜单收缩按钮 -->
							<footer class="footer lt hidden-xs b-t b-dark">
								<a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon">
									<i class="fa fa-angle-left text"></i> 
									<i class="fa fa-angle-right text-active"></i> 
								</a>
							</footer>
						</section>
					</aside>
					<!-- /.aside -->
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder" style="padding-left: 0px;padding-right: 0px;">
								<iframe id="main" name="main" height="1200px" width="100%" scrolling="auto"
									frameborder="0" src="<%= basePath %>toWelcome" >
					
								</iframe>
							</section>
						</section>
					</section>
				</section>
			</section>
		</section>
		
		<script type="text/javascript" src="<%= basePath %>js/app.v2.js"></script>
	</body>
</html>