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
		<title>新增角色</title>
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
									<li>
										<a href="<%= basePath %>role/roles">
											角色管理
										</a>
									</li>
									<li class="active">新增角色</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">新增角色</h2>
								</div>
								
								<form id="form" action="<%= basePath %>role/addRole" method="post" 
										class="form-horizontal" data-validate="parsley">
								
									<!-- 表单提交按钮 -->
	                                <div>
	                                	<a href="<%= basePath %>role/roles" class="btn btn-default">
	                                		<i class="fa  fa-chevron-left"></i>取消
	                                	</a>
	                                    <button type="submit" class="btn btn-dark">添加</button>
	                                    <button type="reset" class="btn btn-warning">重置</button>
	                                </div>
	                                <br />
									
									<!--增加/修改表单-->
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">角色信息</header>
		                                <div class="panel-body">
		                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	角色名称<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="rolename" class="form-control" 
	                                                	data-rangelength="[4,15]" data-notblank="true" 
	                                                	data-required="true" placeholder="请输入角色名称..."
	                                                	data-regexp="[\u4E00-\u9FA5\uF900-\uFA2D]+">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	角色描述
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="roledesc" class="form-control" 
	                                                	data-notblank="true" data-maxlength="300" placeholder="请输入角色描述...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">角色状态</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select name="rolestate" class="form-control m-b">
	                                                    <option value="1" selected="selected">正常</option>
	                                                    <option value="0">禁用</option>
	                                                </select>
	                                                <span><font color="red">一旦某个角色被禁用，则具有相应角色的用户将登录失败！</font></span>
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
	    <script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
	</body>

</html>