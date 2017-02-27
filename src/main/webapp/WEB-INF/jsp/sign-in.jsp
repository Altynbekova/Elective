<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>
<fmt:message key="title.sign.in" var="title"/>
<fmt:message key="placeholder.login" var="loginPlaceholder"/>
<fmt:message key="placeholder.password" var="passwordPlaceholder"/>
<c:set var="signInError" value="${signInError}"/>

<t:page-template title="${title}">

    <jsp:body>
        <div class="row col-sm-4 col-sm-offset-3">
        <form action="${pageContext.request.contextPath}/do/?action=sign-in" method="post" class="form-horizontal">

            <c:if test="${not empty signInError}">
                <div class="alert alert-danger alert-dismissable fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <fmt:message key="${signInError}"/>
                </div>
            </c:if>
           <div class="form-group">
                <label for="login"><fmt:message key="label.login"/></label>
                <input class="form-control" type="text" minlength="3" maxlength="15"
                       id="login" placeholder="<fmt:message key="placeholder.login"/>" name="login" required>
                <c:if test="${not empty loginErrors}">
                    <t:error-messages errors="${loginErrors}"></t:error-messages>
                </c:if>
            </div>

            <div class="form-group">
                <label for="psw"><fmt:message key="label.password"/></label>
                <input class="form-control" type="password" minlength="8" maxlength="15"
                       id="psw" placeholder="<fmt:message key="placeholder.password"/>" name="password" required>
                <c:if test="${not empty passwordErrors}">
                    <t:error-messages errors="${passwordErrors}"></t:error-messages>
                </c:if>
            </div>

            <button type="submit" class="btn btn-success btn-block"><fmt:message
                    key="button.sign.in"/></button>

        </form>
        </div>
    </jsp:body>

</t:page-template>
