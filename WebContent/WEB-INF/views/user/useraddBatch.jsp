<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html class="app">

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
										<a href="<%= basePath %>user/users">
											用户管理
										</a>
									</li>
									<li class="active">批量新增用户</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">批量新增用户</h2>
								</div>
								
								<!-- 增删改结果提示 -->
								<jsp:include page="/common/top.jsp"></jsp:include>
								
								<form id="form" action="<%= basePath %>user/addBatch" method="post" 
									class="form-horizontal" enctype="multipart/form-data" data-validate="parsley">
									
									<!-- 表单提交按钮 -->
                                    <div>
                                    	<a href="<%= basePath %>user/users" class="btn btn-default">
                                    		<i class="fa  fa-chevron-left"></i>取消
                                    	</a>
                                    	<button type="submit" class="btn btn-dark">批量导入</button>
                                    	<a href="<%= basePath %>user/downloadReadFile" class="btn btn-primary">下载使用说明</a>
                                    	<a href="<%= basePath %>user/downloadExcel" class="btn btn-info">下载模板文件</a>
                                    </div>
                                    <br />
                                    
                                    <div class="alert alert-success">
                  						<h4><b>如何批量导入用户信息？(请认真阅读后再操作！)</b></h4>
                  						<p style="font-size: 16px;">第一步：您需要按照固定格式上传用户信息Excel电子表格文件（不支持Excel 2007以上版本新格式，即xlsx格式！）。具体格式可以点击“<b>获得用户信息模板</b>”按钮下载模板。</p>
                  						<p style="font-size: 16px;">第二步：您需要按照格式正确填写表格数据，示例数据在上传前必须删除，但是不可以删除或者修改表头的内容和改变列的数据，否则您的数据将无法正确导入。您只能使用Excel表格的第一个工作簿并且不能修改工作簿的默认名称。</p>
                  						<p style="font-size: 16px;">第三步：在您确认用户信息填写无误后，请点击“选择文件”按钮来选择待上传的用户信息Excel电子表格文件，然后点击“批量导入”按钮来完成导入。</p>
                  						<p style="font-size: 16px;">第四步：您可以根据网页上显示的提示信息判断操作是否执行成功。绿色提示信息代表操作成功，红色提示信息代表操作失败。</p>
                                    </div>
								
									<!--增加/修改表单-->
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">用户信息电子表格</header>
	                                	
		                                <div class="panel-body">
		                                        
	                                        <div class="form-group">
	                                            <label class="col-sm-4 control-label">
	                                            	用户信息Excel电子表格文件<font color="red">（必选）</font>
	                                            </label>
	                                            <div class="col-sm-5">
	                                                <input type="file" name="file" class="filestyle" 
	                                                	data-icon="false" data-required="true" 
	                                                	data-classButton="btn btn-default" 
	                                                	data-classInput="form-control inline input-s"/>
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
		<script type="text/javascript" src="<%= basePath %>js/file-input/bootstrap-filestyle.min.js"></script>
		
		<!-- 校验 -->
	    <script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
	</body>

</html>