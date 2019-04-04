<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>提示信息</title>
	</head>

	<body>
		
		<!-- 增删改结果提示 -->
		<c:if test="${msg != null}">
			<c:choose>
				<c:when test="${result == true}">
					<div class="alert alert-success" style="font-size: 18px;text-align: center;">${msg}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger" style="font-size: 18px;text-align: center;">${msg}</div>
				</c:otherwise>
			</c:choose>
		</c:if>
	</body>

</html>