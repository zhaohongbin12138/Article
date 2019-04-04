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
		<title>部署新流程</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
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
										<a href="<%= basePath %>workflow/deploys">
											流程部署管理
										</a>
									</li>
									<li class="active">部署新流程</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">部署新流程</h2>
									
								</div>
								
								<!-- 表单 -->
								<form id="form" action="<%= basePath %>workflow/deployNewProcess" method="post" enctype="multipart/form-data" data-validate="parsley">
									<div>
										<!-- 返回流程定义列表 -->
										<div>
											<button class="btn btn-default" onclick="javascript:history.go(-1);return false;"><i class="fa  fa-chevron-left"></i>返回</button>
											<button type="submit" class="btn btn-dark btn-s-xs">部署流程</button>
										</div>
									
										<br /><br />
										<section class="panel panel-default">
	                                		<header class="panel-heading font-bold">上传流程部署文件</header>
		                                		<div class="panel-body">
		                                			<div class="form-group">
			                                            <label class="col-sm-4 control-label">
			                                            	流程部署文件<font color="red">（必填）</font>
			                                            </label>
			                                            <div class="col-sm-5">
			                                                <!-- 文件上传表单 -->
														<input id="process" type="file" name="file" data-required="true" reqdata-icon="false" name="process"
							                        		data-classButton="btn btn-default" data-classInput="form-control inline input-s"/>
			                                            </div>
			                                        </div>
												</div>
											</header>
										</section>
									</div>
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
		<script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
		<script type="text/javascript" src="<%= basePath %>js/file-input/bootstrap-filestyle.min.js"></script>
	</body>

</html>