<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>首页</title>
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />
	</head>
	
	<body>
		<!-- 面包屑导航 -->
		<ul class="breadcrumb no-border no-radius b-b b-light">
			<li>
				<a href="<%= basePath %>toWelcome">
					<i class="fa fa-home"></i> 首页
				</a>
			</li>
		</ul>
		
		<!-- 欢迎信息 -->
		<section class="panel panel-default" style="border-bottom: 0px;margin-left: 15px; margin-right: 15px;">
			<div class="hero-unit well" style="background: url(<%= basePath%>images/bg.jpg);">
				<h1 style="color: #FFFFFF;">
					${sessionScope.user.usertruename}，您好！欢迎使用公文管理系统！
				</h1>
				<h3 style="color: #FFFFFF;">
					本系统旨在帮助您提高工作效率，更从容地应付公文管理工作中的问题！
				</h3>
				<p>
					<a class="btn btn-primary btn-large" href="<%= basePath %>article/toAdd">撰写新公文</a>
				</p>
			</div>
		</section>
		
		<h3 style="text-align: center;">公文数据统计</h3>
		<section class="panel panel-default" style="margin-left:15px; margin-right: 15px;">
			<div class="row m-l-none m-r-none bg-light lter">
				<!-- 收到公文 -->
				<div class="col-sm-6 col-md-3 padder-v b-r b-light">
					<span class="fa-stack fa-2x pull-left m-r-sm">
						<i class="fa fa-circle fa-stack-2x text-success"></i>
						<i class="fa fa-envelope fa-stack-1x text-white"></i>
					</span>
					<a class="clear" href="<%= basePath %>article/getMyReceiveList">
						<span class="h3 block m-t-xs">
							<strong>${receivecount}</strong>
						</span>
						<small class="text-muted text-uc">收到</small>
					</a>
				</div>
				
				<!-- 待处理公文：只有审核人员和管理员才可以看到 -->
				<c:if test="${sessionScope.user.roleId == 1 || sessionScope.user.roleId == 2}">
					<div class="col-sm-6 col-md-3 padder-v b-r b-light">
						<span class="fa-stack fa-2x pull-left m-r-sm">
							<i class="fa fa-circle fa-stack-2x text-warning"></i>
							<i class="fa fa-flask fa-stack-1x text-white"></i>
						</span>
						<a class="clear" href="<%= basePath %>article/toAduit">
							<span class="h3 block m-t-xs">
								<strong>${dealcount}</strong>
							</span>
							<small class="text-muted text-uc">待处理</small>
						</a>
					</div>
				</c:if>
				
				<!-- 被驳回公文 -->
				<div class="col-sm-6 col-md-3 padder-v b-r b-light">
					<span class="fa-stack fa-2x pull-left m-r-sm">
						<i class="fa fa-circle fa-stack-2x text-danger"></i>
						<i class="fa fa-ban fa-stack-1x text-white"></i>
					</span>
					<a class="clear" href="<%= basePath %>article/toAduitResult">
						<span class="h3 block m-t-xs">
							<strong>${failcount}</strong>
						</span>
						<small class="text-muted text-uc">被驳回</small>
					</a>
				</div>

				<!-- 等待审核通过公文 -->
				<div class="col-sm-6 col-md-3 padder-v b-r b-light">
					<span class="fa-stack fa-2x pull-left m-r-sm">
						<i class="fa fa-circle fa-stack-2x icon-muted"></i>
						<i class="fa fa-clock-o fa-stack-1x text-white"></i>
					</span>
					<a class="clear" href="<%= basePath %>article/toAduitResult">
						<span class="h3 block m-t-xs">
							<strong>${waitcount}</strong>
						</span>
						<small class="text-muted text-uc">审核中</small>
					</a>
				</div>
			</div>
		</section>
		
		<!-- 小贴士 -->
		<div class="col-lg-6">
			<section class="panel panel-default">
				<div class="carousel slide auto panel-body" id="c-slide">
					<ol class="carousel-indicators out">
						<li data-target="#c-slide" data-slide-to="0" class="active"></li>
						<li data-target="#c-slide" data-slide-to="1" class=""></li>
						<li data-target="#c-slide" data-slide-to="2" class=""></li>
					</ol>
					<div class="carousel-inner">
						<div class="item active">
							<p class="text-center"> 
								<em class="h4 text-mute">极大地提高您的工作效率</em><br>
								<small class="text-muted">系统内置很多小组件，帮助您快速地完成工作任务！</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">适应多种终端，随时随地工作</em><br>
								<small class="text-muted">
									响应式网页设计使得您可以在您的移动设备上完成工作，就像在计算机上那样！
								</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">人性化的界面设计</em><br>
								<small class="text-muted">界面设计使得您与系统的交互轻松而自然，帮助您快速适应系统环境！</small> 
							</p>
						</div>
					</div>
					<!-- 左右切换键 -->
					<a class="left carousel-control" href="#c-slide" data-slide="prev"> 
						<i class="fa fa-angle-left"></i> 
					</a>
					<a class="right carousel-control" href="#c-slide" data-slide="next"> 
						<i class="fa fa-angle-right"></i> 
					</a>
				</div>
			</section>
		</div>
		
		<!-- 公文管理流程 -->
		<div class="col-lg-6">
			<section class="panel bg-dark">
				<div class="carousel slide carousel-fade panel-body" id="c-fade">
					<ol class="carousel-indicators out">
						<li data-target="#c-fade" data-slide-to="0" class="active"></li>
						<li data-target="#c-fade" data-slide-to="1" class=""></li>
						<li data-target="#c-fade" data-slide-to="2" class=""></li>
						<li data-target="#c-fade" data-slide-to="3" class=""></li>
						<li data-target="#c-fade" data-slide-to="4" class=""></li>
						<li data-target="#c-fade" data-slide-to="5" class=""></li>
					</ol>
					<div class="carousel-inner">
						<div class="item active">
							<p class="text-center"> 
								<em class="h4 text-mute">公文处理流程</em><br>
								<small class="text-muted">下面来简单介绍一下系统中的公文处理流程。</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">Step 1:公文撰稿</em><br>
								<small class="text-muted">通过向导，您可以快速上传一篇公文来完成公文撰稿！</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">Step 2:公文提交</em><br>
								<small class="text-muted">撰稿完成后您可以提交公文，提交后公文将进入审核阶段。</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">Step 3:公文审核</em><br>
								<small class="text-muted">如果您是审核人员，您可以审核用户上传的公文。否则，您需要耐心等待审核结果。</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">Step 4:公文发布</em><br>
								<small class="text-muted">审核通过的公文将被发布，目标用户将会收到公文。</small> 
							</p>
						</div>
						<div class="item">
							<p class="text-center"> 
								<em class="h4 text-mute">FAQ:我的公文被驳回怎么办？</em><br>
								<small class="text-muted">审核不通过的公文会被驳回，您可以修改或删除被驳回的公文。</small> 
							</p>
						</div>
					</div>
					<a class="left carousel-control" href="#c-fade" data-slide="prev"> 
						<i class="fa fa-angle-left"></i> 
					</a>
					<a class="right carousel-control" href="#c-fade" data-slide="next"> 
						<i class="fa fa-angle-right"></i> 
					</a>
				</div>
			</section>
		</div>
	</body>
	<script type="text/javascript" src="<%= basePath %>js/app.v2.js"></script> 

</html>