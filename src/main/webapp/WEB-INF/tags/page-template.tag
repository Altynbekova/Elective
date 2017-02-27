<%@tag description="Webpage template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ctg" uri="customtag" %>

<fmt:setBundle basename="language"/>

<%@attribute name="title" required="true" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="login" value="${sessionScope.user.login}"/>
<c:set var="signInError" value="${signInError}"/>
<c:set var="STUDENT" value="STUDENT"/>
<c:set var="LECTURER" value="LECTURER"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <script src="${contextPath}/js/jquery.min.js"></script>
    <script src="${contextPath}/js/bootstrap.min.js"></script>
    <script src="${contextPath}/js/script.js"></script>
    <title>${title}</title>
</head>

<body id="myPage">
<!--Header-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${contextPath}/do/?action=show-main-page">
                <fmt:message key="logo.name"/>
            </a>
        </div>

        <div class="collapse navbar-collapse" id="myNavbar">
            <form class="navbar-form navbar-left" action="${contextPath}/do/?action=find-course"
                  method="post">
                <div class="input-group input-group-sm">
                    <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.search"/>"
                           name="courseName">
                    <div class="input-group-btn ">
                        <button class="btn btn-default" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </div>
                </div>
            </form>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <fmt:message key="locale.change"/><span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li onclick="$.get('${contextPath}/do/?action=set-locale&locale=en');location.reload();">
                            <a href="#"><fmt:message key="locale.english"/></a>
                        </li>
                        <li onclick="$.get('${contextPath}/do/?action=set-locale&locale=ru');location.reload();">
                            <a href="#"><fmt:message key="locale.russian"/></a>
                        </li>
                    </ul>
                </li>

                <c:if test="${empty login}">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <fmt:message key="button.sign.up"/><span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a data-toggle="modal"
                                   href="${contextPath}/do/?action=show-sign-up-form&role=lecturer"><fmt:message
                                    key="role.lecturer"/></a>
                            </li>
                            <li><a data-toggle="modal"
                                   href="${contextPath}/do/?action=show-sign-up-form&role=student"><fmt:message
                                    key="role.student"/></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a data-toggle="modal" href="#signInModal"><span
                                class="glyphicon glyphicon-log-in"></span>
                            <fmt:message key="button.sign.in"/></a>
                    </li>
                </c:if>

                <c:if test="${not empty login}">
                    <li>
                        <p class="navbar-text"><fmt:message key="tag.hello"/><ctg:hello
                                login="${sessionScope.user.login}"/>!</p>
                    </li>
                    <li>
                        <a href="${contextPath}/do/?action=sign-out"><span class="glyphicon glyphicon-log-out"></span>
                            <fmt:message key="button.sign.out"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<!--Sign in modal-->
<div class="modal fade" id="signInModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content col-xs-8 col-xs-offset-2">
            <!-- header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="text-center"><fmt:message key="modal.title.sign.in"/></h4>
            </div>
            <!-- body-->
            <div class="modal-body">
                <c:if test="${not empty signInError}">
                    <div class="alert alert-danger"><fmt:message key="${signInError}"/></div>
                </c:if>
                <form name="signInForm" method="post" action="${contextPath}/do/?action=sign-in">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control" id="login" minlength="3" maxlength="15"
                                   placeholder="<fmt:message key="placeholder.login"/>" name="login" required>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" class="form-control" minlength="8" maxlength="15"
                                   placeholder="<fmt:message key="placeholder.password"/>" name="password" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-default btn-success btn-block">
                        <fmt:message key="button.sign.in"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="container-fluid">
    <div class="row">
        <!--Side nav (Menu)-->
        <div class="col-sm-3">
            <ul class="list-group dropdown">
                <c:if test="${sessionScope.user.role==LECTURER}">
                    <li class="list-group-item"><a href="${contextPath}/do/?action=show-lecturer-courses">
                        <fmt:message key="menu.my.courses"/>
                    </a></li>
                    <li class="list-group-item"><a href="${contextPath}/do/?action=show-new-course-form">
                        <fmt:message key="menu.lecturer.add.course"/>
                    </a></li>
                </c:if>

                <c:if test="${sessionScope.user.role==STUDENT}">
                    <li class="dropdown list-group-item"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="menu.my.courses"/>
                        <span class="caret"></span>
                    </a>
                        <ul class="dropdown-menu">
                            <li><a href="${contextPath}/do/?action=show-student-courses&isComplete=true">
                                <fmt:message key="menu.my.courses.completed"/>
                            </a></li>
                            <li><a href="${contextPath}/do/?action=show-student-courses&isComplete=false">
                                <fmt:message key="menu.my.courses.incomplete"/>
                            </a></li>
                        </ul>
                    </li>
                </c:if>

                <li class="list-group-item"><a href="${contextPath}/do/?action=show-all-courses">
                    <fmt:message key="menu.all.courses"/>
                </a></li>
            </ul>
        </div>

        <!--Main (Content)-->
        <div class="col-sm-9 text-justify">
            <jsp:doBody/>
        </div>
    </div>
</div>


<!--Footer-->
<footer class="container-fluid text-center">
    <a class="up-arrow" href="#myPage" data-toggle="tooltip" data-original-title="<fmt:message key="tooltip.top"/>">
        <span class="glyphicon glyphicon-chevron-up"></span>
    </a>
</footer>

</body>
</html>

