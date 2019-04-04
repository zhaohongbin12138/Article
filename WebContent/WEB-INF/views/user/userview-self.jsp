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
		<title>查看我的信息</title>
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
									<li class="active">我的个人信息</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">我的个人信息</h2>
								</div>
								
								<!-- 增删改结果提示 -->
								<jsp:include page="/common/top.jsp"></jsp:include>
								
								<!-- 返回按钮 -->
                                <div>
                                	<a href="<%= basePath %>toWelcome" class="btn btn-default btn-sm">
                                		<i class="fa  fa-chevron-left"></i>返回首页
                                	</a>
                                	<a href="<%= basePath %>user/toModifySelf" class="btn btn-warning btn-sm">修改个人信息</a>
                                	<a href="<%= basePath %>user/toPasswordModify" class="btn btn-danger btn-sm">修改我的密码</a>
								</div>
								<br />
								
								<!--增加/修改表单-->
            					<section class="panel panel-default">
                                	<header class="panel-heading font-bold">用户信息</header>
	                                <div class="panel-body">
	                                	<form action="#" class="form-horizontal">
                                        	<div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	用户名
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" class="form-control" readonly="readonly" 
	                                                	value="${sessionScope.user.loginname}" placeholder="用户名没加载出来">
	                                            </div>
	                                        </div>

	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	用户真实姓名
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" class="form-control" readonly="readonly"
	                                                	value="${sessionScope.user.usertruename}" placeholder="用户真实姓名没加载出来">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	职务
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" class="form-control" readonly="readonly"
	                                                	value="${sessionScope.user.job}" placeholder="职务没加载出来">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	手机
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" class="form-control" readonly="readonly"
	                                                	value="${sessionScope.user.phone}" placeholder="手机号码没加载出来">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	邮箱
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="email" class="form-control" readonly="readonly"
	                                                	value="${sessionScope.user.email}" placeholder="邮箱没加载出来">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">用户状态</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select class="form-control m-b" disabled="disabled">
	                                                    <option value="1" <c:if test="${sessionScope.user.userstate == 1}">selected="selected"</c:if>>正常</option>
	                                                    <option value="0" <c:if test="${sessionScope.user.userstate == 0}">selected="selected"</c:if>>禁用</option>
	                                                </select>
	                                                <span><font color="red">一旦某个用户被禁用，则该用户将登录失败！</font></span>
	                                            </div>
	                                        </div>
	                                        
	                                    </form>
	                                </div>
            					</section>
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
	</body>

</html>