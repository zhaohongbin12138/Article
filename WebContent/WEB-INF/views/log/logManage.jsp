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
		<title>日志管理</title>
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
									<li class="active">日志管理</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h3 class="m-b-none">系统日志信息</h3>
								</div>
								
								<!-- 增删改结果提示 -->
								<jsp:include page="/common/top.jsp"></jsp:include>

								<section class="panel panel-default">
									<header class="panel-heading">日志信息列表</header>
									
									<!-- 提交搜索条件和分页信息 -->
									<form action="<%= basePath %>log/logs" method="post" 
											data-validate="parsley">
										
										<!-- 查询条件 -->
										<div class="row text-sm wrapper">
											<!-- 左侧条件 -->
											<div class="col-sm-10 m-b-xs">
												<div class="inline" style="margin-right:20px;">
													操作名称
													<input type="text" id="keyword" name="keyword" class="input-sm form-control inline" 
														style="width: 150px;" placeholder="请输入操作名称" value="${keyword}"
														data-rangelength="[4,30]">
														
													<button class="btn btn-sm btn-default">查询</button>
												</div>
	
											</div>
										</div>
										
										<!-- 表格主体和数据 -->
										<div class="table-responsive">
											<table class="table table-striped b-t b-light text-sm">
												<!-- 顶表头 -->
												<thead>
													<tr>
														<th style="text-align: center;">操作名称</th>
														<th style="text-align: center;">执行人</th>
														<th style="text-align: center;">Ip地址</th>
														<th style="text-align: center;">执行时间</th>
													</tr>
												</thead>
												
												<!-- 数据 -->
												<tbody>
													
													<%-- 判断有无数据 --%>
													<c:choose>
														<c:when test="${!empty page.list}">
															<c:forEach items="${page.list}" var="log">
																<tr>
																	<td style="line-height: 30px;text-align: center;">${log.optname}</td>
																	<td style="line-height: 30px;text-align: center;">${log.operator.usertruename}</td>
																	<td style="line-height: 30px;text-align: center;">${log.ipaddress}</td>
																	<td style="line-height: 30px;text-align: center;">
																		<fmt:formatDate value="${log.opttime}" type="both"/>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														
														<%-- 没数据 --%>
														<c:otherwise>
															<tr>
																<td style="text-align: center;" colspan="4">
																	<div class="alert alert-success" style="font-size: 18px;">没有找到记录</div>
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
												
												</tbody>
												
												<!-- 底表头 -->
												<thead>
													<tr>
														<th style="text-align: center;">操作名称</th>
														<th style="text-align: center;">执行人</th>
														<th style="text-align: center;">Ip地址</th>
														<th style="text-align: center;">执行时间</th>
													</tr>
												</thead>
											</table>
										</div>
										
										<!-- 分页控件 -->
										<jsp:include page="/common/footer.jsp"></jsp:include>
										
									</form>
									
								</section>
							</section>
						</section>
						<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
					</section>
				</section>
			</section>
		</section>
		<script type="text/javascript" src="<%= basePath %>js/app.v2.js"></script>
		<!-- 获取各请求参数，组成URL -->
		<script type="text/javascript">
		
			var host="<%= basePath %>";
			
			//获取请求的URL
			function getRequestURL(page) {
				
				var pageNo = page;
				var pageCount = $("#pageCount").val();
				var keyword = $("#keyword").val();
				
				var url = host + "log/logs?pageNo=" + pageNo + "&pageCount=" + pageCount 
							+ "&keyword=" + keyword;
				return url;
			}
		
		</script>
		<script type="text/javascript" src="<%= basePath %>js/common/manage-common.js"></script>
		<!-- 校验 -->
	    <script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
	</body>

</html>