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
		<title>用户修改个人信息</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	    <link rel="stylesheet" href="<%= basePath %>js/fuelux/fuelux.css" type="text/css"/>
	</head>

	<body>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							
							<section class="scrollable padder">
								<!-- 面包屑导航 -->
								<ul class="breadcrumb no-border no-radius b-b b-light pull-in">
									<li>
										<a href="<%= basePath %>toWelcome">
											<i class="fa fa-home"></i> 首页
										</a>
									</li>
									<li class="active">修改个人信息</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">修改个人信息</h2>
								</div>
								
								<form id="form" action="<%= basePath %>user/modifyself" method="post" class="form-horizontal" data-validate="parsley">
								
									<!-- 表单提交按钮 -->
                                    <div>
                                    	<a href="<%= basePath %>toWelcome" class="btn btn-default btn-sm">
                                    		<i class="fa  fa-chevron-left"></i>返回首页
                                    	</a>
                                    	<button type="submit" class="btn btn-dark">提交</button>
                                    	<button type="reset" class="btn btn-warning">重置</button>
                                    </div>
                                    <br />
								
									<!--增加/修改表单-->
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">我的信息</header>
		                                <div class="panel-body">
		                                    
		                                    <input type="hidden" name="userid" value="${sessionScope.user.userid}">
		                                    
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	职务<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="job" class="form-control" data-required="true" 
	                                                	value="${sessionScope.user.job}" placeholder="请输入用户的职务...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	手机
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="phone" class="form-control" data-type="phone" 
	                                                	value="${sessionScope.user.phone}" placeholder="请输入手机号码...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	邮箱
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="email" name="email" class="form-control" data-type="email"  
	                                                	value="${sessionScope.user.email}" placeholder="请输入邮箱...">
	                                            </div>
	                                        </div>
		                                    
		                                </div>
	            					</section>
            					</form>
							</section>
						</section>
						<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
					</section>
					<aside class="bg-light lter b-l aside-md hide" id="notes">
						<div class="wrapper">Notification</div>
					</aside>
				</section>
			</section>
		</section>

		<script type="text/javascript" src="<%= basePath %>js/app.v2.js" ></script>
		<script type="text/javascript" src="<%= basePath %>js/fuelux/fuelux.js"></script>
		
		<!-- 校验 -->
	    <script src="<%= basePath %>js/parsley/parsley.min.js"></script>
	</body>

</html>