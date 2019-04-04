<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="">
<head>
<meta charset="utf-8" />
<title>系统出错</title>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="<%= basePath %>css/font.css" type="text/css" cache="false" />
<!--[if lt IE 9]> <script src="<%= basePath %>js/ie/html5shiv.js" cache="false"></script> <script src="<%= basePath %>js/ie/respond.min.js" cache="false"></script> <script src="<%= basePath %>js/ie/excanvas.js" cache="false"></script> <![endif]-->
</head>
<body>
	<section id="content">
		<div class="row m-n">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="text-center m-b-lg">
					<h2 class="h text-dark animated fadeInDownBig">出错了！</h2>
				</div>
				<div class="list-group m-b-sm bg-white m-b-lg">
					<a href="<%= basePath %>toWelcome" class="list-group-item"> 
						<i class="fa fa-chevron-right icon-muted"></i>
						<i class="fa fa-fw fa-home icon-muted"></i> 回首页
					</a> 
					
					<a href="mailto:601345138@qq.com" class="list-group-item"> 
						<i class="fa fa-chevron-right icon-muted"></i> 
						<span class="badge">601345138@qq.com</span>
						<i class="fa fa-fw fa-phone icon-muted"></i> 联系我们
					</a>
				</div>
			</div>
		</div>
	</section>
	<!-- footer -->
	<footer id="footer">
		<div class="text-center padder clearfix">
			<p>
				<small>王凯的毕业设计——公文管理系统<br>
					&copy; 2017
				</small>
			</p>
		</div>
	</footer>
	<!-- / footer -->
	<script src="<%= basePath %>js/app.v2.js"></script>
	<!-- Bootstrap -->
	<!-- App -->
</body>
</html>
