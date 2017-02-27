<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="language"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="refresh" content="1; url=${pageContext.request.contextPath}/do/?action=show-main-page">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title><fmt:message key="title.register.success"/></title>
</head>
<body>
<div class="jumbotron">
    <div class="container-fluid">
        <h2><fmt:message key="content.registration.success.header"/></h2>
    </div>
</div>
<div class="container-fluid">
    <p><fmt:message key="content.registration.success"/></p>
</div>
</body>
</html>

