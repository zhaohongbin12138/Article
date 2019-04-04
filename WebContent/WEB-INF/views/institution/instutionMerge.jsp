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
		<title>机构合并</title>
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
									<li class="active">机构合并</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">机构合并</h2>
									
								</div>
								
								<form id="form" action="<%= basePath %>institution/merge" method="post" 
										class="form-horizontal" onsubmit="return validate();">
									<div>
										<!-- 表单提交按钮 -->
										<a href="<%= basePath %>institution/institutions" class="btn btn-default">
											<i class="fa  fa-chevron-left"></i>取消
										</a>
                        				<button type="submit" class="btn btn-dark">合并机构</button>
                                		
									</div>
									<br />
									
									<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		1.选择合并机构（至少选1个）
	                                	</header>
		                                <div class="panel-body">
											<h4>在机构名称前面的方框打钩表示该机构将被合并！</h4>
											
											<c:forEach items="${noUserInstitutions}" var="institution">
												<div class="col-sm-3">
					            					<div class="checkbox">
					                                    <label class="checkbox-custom">
					                                        <input type="checkbox" name="instid1" value="${institution.instid}">
					                                        <i class="fa fa-fw fa-square-o"></i> ${institution.instname}
					                                    </label>
					                                </div>
				            					</div>
											</c:forEach>
			            					
	            						</div>
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		2.设置合并后的机构信息
	                                	</header>
		                                <div class="panel-body">
											<h4>选择要将上面选中的这些机构合并为哪一个机构</h4>
											
											<c:forEach items="${validInstitution}" var="institution" varStatus="st">
												<div class="col-sm-3">
					            					<div class="radio">
		                                                <label class="radio-custom">
		                                                    <input type="radio" name="instid2" value="${institution.instid}"
		                                                    	<c:if test="${st.index == 0}">checked = "checked"</c:if>
		                                                    >
		                                                    <i class="fa fa-circle-o"></i> ${institution.instname} 
		                                                </label>
		                                            </div>
				            					</div>
											</c:forEach>
			            					
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
		<script type="text/javascript">
		
			function validate() {
				
				if ($(":checkbox[name='instid1']:checked").length == 0) {
					alert("请选中至少一个被合并机构!");
					return false;
				}
				
				return true;
			}
		
		</script>
		<script type="text/javascript" src="<%= basePath %>js/fuelux/fuelux.js"></script>
	</body>

</html>