<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>公文信息查看与预览</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="stylesheet" href="<%= basePath %>css/itranswarp.css" type="text/css" />
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
									<li>公文管理</a>
									</li>
									<li class="active">公文信息查看与预览</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">公文信息查看与预览</h2>
									
								</div>
								
								<form action="#" method="post" class="form-horizontal">
									<div>
                                		<a href="#" class="btn btn-default" onclick="javascript:history.go(-1);return false">
                                			<i class="fa  fa-chevron-left"></i>返回
                                		</a>
                                		<c:forEach items="${article.attachments}" var="attachment">
											<c:if test="${attachment.attachtype == 0}">
												<a href="<%= basePath %>article/download?attachmentid=${attachment.attachmentid}&fileId=${attachment.fileid}" class="btn btn-primary">
													<i class="fa fa-download"></i>下载
												</a>
											</c:if>
										</c:forEach>
									</div>
									<br />
									
									<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		公文基本信息
	                                	</header>
		                                
		                                <div>
											<table cellpadding="1" cellspacing="1"
												style="margin: 10px; width: 98%;">
												<!-- 公文题目 -->
												<tr>
													<td colspan="4" style="text-align: center; font-size: 25px;">
														${article.title}
													</td>
												</tr>
												
												<!-- 公文的一些基本信息 -->
												<tr>
													<td width="15%" style="padding: 10px;"><b>撰稿人：</b></td>
													<td width="35%">${article.copywriter.usertruename}</td>
													<td width="15%" style="padding: 10px;"><b>审稿人：</b></td>
													<td width="35%">${article.auditor.usertruename}</td>
												</tr>
												
												<tr>
													<td width="15%" style="padding: 10px;"><b>发布时间：</b></td>
													<td width="35%">
														<fmt:formatDate value="${article.publishtime}" type="both"/>
													</td>
													<td width="15%" style="padding: 10px;"><b>发布机构：</b></td>
													<td width="35%">${article.institution.instname}</td>
												</tr>
												
												<tr>
													<td width="15%" style="padding: 10px;"><b>浏览：</b></td>
													<td width="35%">${article.clickcount}次</td>
													<td width="15%" style="padding: 10px;"><b>下载：</b></td>
													<td width="35%">${article.downloadcount}次</td>
												</tr>
												
											</table>
												
		                                </div>
		                                
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		能看到公文的组织和个人
	                                	</header>
	                                
	                                	<div>
											<table cellpadding="1" cellspacing="1"
												style="margin: 10px; width: 98%;">
												<tr>
													<td style="padding: 10px;" width="20%"><b>能看到的用户：</b></td>
													<td style="padding: 10px;" width="80%">
														<c:forEach items="${article.receivers}" var="receiver">
															<code>${receiver.usertruename}（${receiver.institution.instname}）</code>
														</c:forEach>
													</td>
												</tr>
											</table>
		                                </div>
	                                </section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		公文携带的附件
	                                	</header>
		                                
		                                <div class="table-responsive">
											<table class="table table-striped b-t b-light text-sm">
												<thead>
													<tr>
														<td style="padding: 10px; text-align: center;" width="85%"><b>名称</b></td>
														<td style="padding: 10px; text-align: center;" width="15%"><b>操作</b></td>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<%-- 有数据 --%>
														<c:when test="${!empty article.attachments}">
															<c:forEach items="${article.attachments}" var="attachment">
																<c:if test="${attachment.attachtype == 1}">
																	<tr>
																		<td style="padding: 10px;">${attachment.filename}</td>
																		<td style="padding: 10px; text-align: center;">
																			<a href="<%= basePath %>article/download?attachmentid=${attachment.attachmentid}&fileId=${attachment.fileid}" class="btn btn-primary">
																				<i class="fa fa-download"></i>下载
																			</a>
																		</td>
																	</tr>
																</c:if>
															</c:forEach>
														</c:when>
														<%-- 没数据 --%>
														<c:otherwise>
															<tr>
																<td style="text-align: center;" colspan="2">
																	<div class="alert alert-success" style="font-size: 18px;">没有附件</div>
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
													
												</tbody>
											</table>
										</div>
		                                
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		公文审核信息
	                                	</header>
		                                
		                                <div class="table-responsive">
											<table class="table table-striped b-t b-light text-sm">
												<thead>
													<tr>
														<td style="padding: 10px; text-align: center;" width="70%"><b>审核意见</b></td>
														<td style="padding: 10px; text-align: center;" width="15%"><b>审核结果</b></td>
														<td style="padding: 10px; text-align: center;" width="15%"><b>审核时间</b></td>
													</tr>
												</thead>
												<tbody>
												
													<%-- 判断有无数据 --%>
													<c:choose>
														<c:when test="${!empty article.auditMessages}">
															<c:forEach items="${article.auditMessages}" var="auditMessage">
																<tr>
																	<td style="padding: 10px; text-align: center;">${auditMessage.auditmessage}</td>
																	<td style="padding: 10px; text-align: center;">
																		<c:choose>
																			<c:when test="${auditMessage.auditresult == 0}">驳回</c:when>
																			<c:when test="${auditMessage.auditresult == 1}">通过</c:when>
																		</c:choose>
																	</td>
																	<td style="padding: 10px; text-align: center;">
																		<fmt:formatDate value="${auditMessage.auditdate}" type="both"/>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														
														<%-- 没数据 --%>
														<c:otherwise>
															<tr>
																<td style="text-align: center;" colspan="3">
																	<div class="alert alert-success" style="font-size: 18px;">没有审核记录</div>
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
													
												</tbody>
											</table>
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
	</body>

</html>