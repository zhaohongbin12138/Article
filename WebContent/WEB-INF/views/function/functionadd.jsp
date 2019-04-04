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
		<title>新增功能</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	    <link rel="stylesheet" href="<%= basePath %>js/fuelux/fuelux.css" type="text/css" cache="false" />
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
										<a href="<%= basePath %>fun/funs">
											功能管理
										</a>
									</li>
									<li class="active">新增功能</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">新增功能</h2>
								</div>
								
								<form id="form" action="<%= basePath %>fun/addFunction" method="post" 
									class="form-horizontal" data-validate="parsley">
								
									<!-- 表单提交按钮 -->
	                                <div>
	                                	<a href="<%= basePath %>fun/funs" class="btn btn-default">
	                                		<i class="fa  fa-chevron-left"></i>返回
	                                	</a>
	                                    <button type="submit" class="btn btn-dark">添加</button>
	                                    <button type="reset" class="btn btn-warning">重置</button>
	                                </div>
	                                <br />
									
									<!--增加/修改表单-->
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">功能信息</header>
		                                <div class="panel-body">
		                                    	
	                                    	<div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	父功能
	                                            </label>
	                                            <div class="col-sm-5">
	                                            	<!-- 这里只能加载父功能和顶层功能，不加载子功能，从而限制功能列表只能是二级的 -->
	                                                <select name="funpid" class="form-control m-b">
	                                                	<!-- 加载出备选项 -->
	                                                	<c:forEach items="${functionList}" var="function">
	                                                		<option value="${function.funid}">${function.funname}</option>
	                                                	</c:forEach>
	                                                </select>
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	功能名称<font color="red">（必填）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="funname" class="form-control" data-rangelength="[4,15]" 
	                                                	data-required="true" data-notblank="true" data-regexp="[\u4E00-\u9FA5\uF900-\uFA2D]+"
	                                                	placeholder="请输入功能名称...">
	                                            </div>
	                                        </div>
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	功能URL
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="text" name="funurl" class="form-control" data-rangelength="[2,100]" 
	                                                	 data-notblank="true" placeholder="请输入功能URL...">
	                                            </div>
	                                        </div>
	                                        
	                                        
	                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">功能状态</label>
	                                            
	                                            <div class="col-sm-5">
	                                                <select name="funstate" class="form-control m-b">
	                                                    <option value="1" selected="selected">正常</option>
	                                                    <option value="0">禁用</option>
	                                                </select>
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