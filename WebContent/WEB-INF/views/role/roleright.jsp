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
		<title>权限分配</title>
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
									<li class="active">权限分配</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">权限分配（${role.rolename}）</h2>
									
								</div>
								
								<form id="form" action="<%= basePath %>role/updateRoleRight" method="post" class="form-horizontal">
									<div>
										<!-- 表单提交按钮 -->
										<a href="<%= basePath %>role/roles" class="btn btn-default"><i class="fa  fa-chevron-left"></i>取消</a>
                        				<button type="submit" class="btn btn-dark">保存权限设置</button>
									</div>
									<br />
									
									<input type="hidden" name="roleid" value="${role.roleid}">
									
									<section class="panel panel-default">
	                                	<header class="panel-heading font-bold">
	                                		权限列表
	                                	</header>
		                                <div class="panel-body">
											<h4>在功能名称前面的方框打钩表示该角色能够使用此功能！</h4>
											
			            					<div class="col-sm-6">
			            						<c:forEach items="${functionList}" var="parent">
			            							<c:if test="${parent.funpid == -1 && parent.funid != -1}">
			            								<!-- 加载顶级功能 -->
				            							<div class="checkbox">
						                                    <label class="checkbox-custom">
						                                        <input id="${parent.funid}" type="checkbox" name="funids" value="${parent.funid}"
						                                        		onclick="checkBox('${parent.funid}', '${parent.funpid}');"
						                                        	<%-- 回显选项 --%>
						                                        	<c:forEach items="${role.functionList}" var="fun">
						                                        		<c:if test="${parent.funid == fun.funid}">checked="checked"</c:if>
						                                        	</c:forEach>
						                                        >
						                                        <i id="i${parent.funid}" class="fa fa-fw fa-square-o"></i>${parent.funname}
						                                    </label>
						                                    <br />
						                                    <!-- 显示子功能 -->
						                                    <c:forEach items="${functionList}" var="child">
						                                    	<c:if test="${child.funpid == parent.funid}">
						                                    		<!-- 子功能 -->
								                                    <div class="checkbox">
									                                    <label class="checkbox-custom">
									                                        <input id="${child.funid}" type="checkbox" name="funids" value="${child.funid}" parent="${child.funpid}"
									                                        		onclick="checkBox('${child.funid}', '${child.funpid}');"
									                                        	<%-- 回显选项 --%>
									                                        	<c:forEach items="${role.functionList}" var="fun">
									                                        		<c:if test="${child.funid == fun.funid}">checked="checked"</c:if>
									                                        	</c:forEach>
									                                        >
									                                        <i id="i${child.funid}" class="fa fa-fw fa-square-o"></i> ${child.funname}
									                                    </label>
									                                </div>
						                                    	</c:if>
						                                    </c:forEach>
							                                
						                                </div>
			            							</c:if>
				            					</c:forEach>	
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
		
		<script type="text/javascript">
		
			function checkBox(funid, funpid) {

				//判断当前节点是否被选中，进而判断是选中操作还是取消操作
				if ($("#i" + funid).attr("class") != "fa fa-fw fa-square-o checked") {  //当前未选中，执行选中操作
					
					//选中了父节点，选中下方所有子节点
					$(":checkbox[parent='" + funid + "']").each(function () {
							$(this).attr("checked", "checked");
							$("#i" + $(this).attr("id")).addClass("checked");
						}
					);
					
					//选中了子节点，则要选中对应的父节点
					if ($("#i" + funpid).attr("class") != "fa fa-fw fa-square-o checked") {  //父节点没被选中
						$("#" + funpid).attr("checked", "checked");
						$("#i" + funpid).addClass("checked");
					}
					
				} else { //否则执行取消操作

					//取消了父节点，取消下方所有子节点
					$(":checkbox[parent='" + funid + "']").each(function () {
							$(this).removeAttr("checked");
							$("#i" + $(this).attr("id")).removeClass("checked");
						}
					);

					//取消了最后一个选中的子节点，则要取消对应的父节点
					if ($(":checkbox[parent='" + funpid + "']:checked").length == 0) {
						
						$("#" + funpid).removeAttr("checked");
						$("#i" + funpid).removeClass("checked");
					}
				}
			}
		
		</script>

		<script type="text/javascript" src="<%= basePath %>js/app.v2.js" ></script>
		<script type="text/javascript" src="<%= basePath %>js/fuelux/fuelux.js"></script>
	</body>

</html>