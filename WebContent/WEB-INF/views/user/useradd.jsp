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
		<title>新增用户</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	    <link rel="stylesheet" href="<%= basePath %>js/fuelux/fuelux.css" type="text/css" />
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
									<li>
										<a href="<%= basePath %>user/users">
											用户管理
										</a>
									</li>
									<li class="active">新增用户</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">新增用户</h2>
								</div>
								
								<form id="form" action="<%= basePath %>user/add" method="post" 
										class="form-horizontal" data-validate="parsley">
									
									<!-- 表单提交按钮 -->
                                    <div>
                                    	<a href="<%= basePath %>user/users" class="btn btn-default">
                                    		<i class="fa  fa-chevron-left"></i>取消
                                    	</a>
                                    	<button type="submit" class="btn btn-dark">提交</button>
                                    	<button type="reset" class="btn btn-warning">重置</button>
                                    </div>
                                    <br />
								
									<!--增加/修改表单-->
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">用户信息</header>
		                                <div class="panel-body">
		                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	用户名<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="loginname" class="form-control" data-required="true" 
	                                                	data-rangelength="[4,30]" data-notblank="true"
	                                                	data-regexp="[A-Za-z0-9]+"
	                                                	placeholder="请输入用户名...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	密码<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input id="password" name="password" type="password" class="form-control" 
	                                                	data-rangelength="[6,15]" data-notblank="true"
	                                                	data-required="true" placeholder="请输入密码...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	确认密码<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input id="confirmPassword" name="confirmPassword" type="password" 
	                                                	class="form-control" data-rangelength="[6,15]" data-notblank="true"
	                                                	data-required="true" data-equalto="#password" placeholder="再输入一遍密码...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	用户真实姓名<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="usertruename" class="form-control" data-required="true" 
	                                                	data-regexp="[\u4E00-\u9FA5\uF900-\uFA2D]+" data-notblank="true"
	                                                	placeholder="请输入用户真实姓名...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	职务<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="job" class="form-control" data-required="true" 
	                                                	data-regexp="[\u4E00-\u9FA5\uF900-\uFA2D]+" data-notblank="true"
	                                                	placeholder="请输入用户的职务...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	手机
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="phone" class="form-control" data-type="phone" 
	                                                	data-notblank="true" placeholder="请输入手机号码...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	邮箱
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="email" name="email" class="form-control" data-type="email" 
	                                                	data-notblank="true" placeholder="请输入邮箱...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">所属机构</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select name="instId" class="form-control m-b">
	                                                	<c:forEach items="${institutionList}" var="institution">
	                                                		<option value="${institution.instid}">${institution.instname}</option>
	                                                	</c:forEach>
	                                                </select>
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">角色</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select name="roleId" class="form-control m-b">
	                                                	<c:forEach items="${roleList}" var="role">
	                                                		<option value="${role.roleid}">${role.rolename}</option>
	                                                	</c:forEach>
	                                                </select>
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">用户状态</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select name="userstate" class="form-control m-b">
	                                                    <option value="1" selected="selected">正常</option>
	                                                    <option value="0">禁用</option>
	                                                </select>
	                                                <span><font color="red">一旦某个用户被禁用，则该用户将登录失败！</font></span>
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