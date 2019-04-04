<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>分页控件</title>
	</head>

	<body>
		
		<footer class="panel-footer">
			<div class="row">
				<div class="col-sm-4" style="float: left;">
					每页显示
					<select id="pageCount" name="pageCount" class="input-sm form-control input-s-sm inline" style="width: 70px;">
						<option value="10" <c:if test="${pageCount == 10}">selected="selected"</c:if>>10</option>
						<option value="20" <c:if test="${pageCount == 20}">selected="selected"</c:if>>20</option>
						<option value="50" <c:if test="${pageCount == 50}">selected="selected"</c:if>>50</option>
						<option value="100" <c:if test="${pageCount == 100}">selected="selected"</c:if>>100</option>
					</select>
					条数据
				</div>
				
				<!-- 分页控件 -->
				<div class="col-sm-6 text-right text-center-xs" style="float: right;">
					<ul class="pagination pagination-sm m-t-none m-b-none">
						<!-- 分页信息 -->
						<li>
							<a href="#">共${page.pages }页,${page.total} 条记录</a>
						</li>
						
						<!-- 首页按钮 -->
						<c:if test="${page.pages != 1}">
							<li><a onclick="gotoPage(1);">首页</a></li>
						</c:if>
						
						
						<c:if test="${page.hasPreviousPage}">
							<li>
								<a aria-label="Previous" onclick="gotoPage(${page.pageNum-1});">
									<i class="fa fa-chevron-left"></i>
								</a>
							</li>
						</c:if>
	
						<%-- 得到连续的5个页码 --%>
						<c:forEach items="${page.navigatepageNums}" var="page_Num">
							<%-- 判断是不是当前页 --%>
							<c:choose>
								<%-- 是当前页就设置不允许点 --%>
								<c:when test="${page_Num == page.pageNum}">
									<li class="active"><a href="#">${page_Num}</a></li>
								</c:when>
								<%-- 否则允许点 --%>
								<c:otherwise>
									<li><a onclick="gotoPage(${page_Num});">${page_Num}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<!-- 根据是否有下一页设置是否生成下一页按钮 -->
						<c:if test="${page.hasNextPage }">
							<li>
								<a onclick="gotoPage(${page.pageNum+1 });" aria-label="Next">
									<i class="fa fa-chevron-right"></i>
								</a>
							</li>
						</c:if>
						
						<!-- 末页 -->
						<c:if test="${page.pages != 1}">
							<li><a onclick="gotoPage(${page.pages});">末页</a></li>
						</c:if>
					
					</ul>
				</div>
			</div>
		</footer>
	</body>

</html>