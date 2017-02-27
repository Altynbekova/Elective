<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.sign.up" var="title"/>
<c:set var="registerError" value="${registerErrors}"/>
<c:set value="${pageContext.request.getParameter('role')}" var="role"/>
<c:set value="${role=='lecturer'?'':'disabled'}" var="disabled"/>

<t:page-template title="${title}">
    <jsp:body>
        <div class="row col-sm-4 col-sm-offset-3">
            <form name="signUpLecturerForm" method="post"
                  action="${pageContext.request.contextPath}/do/?action=sign-up&role=${role}">
                <c:if test="${not empty registerError}">
                    <div class="alert alert-danger"><fmt:message key="${registerError}"/></div>
                </c:if>
                <div class="form-group">
                    <label for="firstName"><fmt:message key="label.firstname"/></label>
                    <input type="text" class="form-control" id="firstName" minlength="3" maxlength="20"
                           placeholder="<fmt:message key="placeholder.firstName"/>" name="firstName" required>
                    <c:if test="${not empty firstNameErrors}">
                        <t:error-messages errors="${firstNameErrors}"></t:error-messages>
                    </c:if>

                </div>

                <div class="form-group">
                    <label for="lastName"><fmt:message key="label.lastname"/></label>
                    <input type="text" class="form-control" id="lastName" minlength="3" maxlength="20"
                           placeholder="<fmt:message key="placeholder.lastName"/>" name="lastName" required>
                    <c:if test="${not empty lastNameErrors}">
                        <t:error-messages errors="${lastNameErrors}"></t:error-messages>
                    </c:if>
                </div>

                <div class="form-group ${disabled}">
                    <label for="jobTitle"><fmt:message key="label.jobtitle"/></label>
                    <input type="text" class="form-control" id="jobTitle" maxlength="20"
                           placeholder="<fmt:message key="placeholder.jobTitle"/>" name="jobTitle" ${disabled}>
                    <c:if test="${not empty jobTitleErrors}">
                        <t:error-messages errors="${jobTitleErrors}"></t:error-messages>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="login"><fmt:message key="label.login"/></label>
                    <input class="form-control" type="text" minlength="3" maxlength="15"
                           id="login" placeholder="<fmt:message key="placeholder.login"/>" name="login" required>
                    <c:if test="${not empty loginErrors}">
                        <t:error-messages errors="${loginErrors}"></t:error-messages>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="password"><fmt:message key="label.password"/></label>
                    <input class="form-control" type="password" minlength="8" maxlength="15"
                           id="password" placeholder="<fmt:message key="placeholder.password"/>" name="password"
                           required>
                    <c:if test="${not empty passwordErrors}">
                        <t:error-messages errors="${passwordErrors}"></t:error-messages>
                    </c:if>
                </div>

                <button type="submit" class="btn btn-default btn-success btn-block"><fmt:message
                        key="button.sign.up"/></button>
            </form>

            <p class="text-right"><fmt:message key="label.have.account"/>
                <a data-toggle="modal" href="#signInModal" data-dismiss="modal"><fmt:message key="button.sign.in"/></a>
            </p>
        </div>
    </jsp:body>
</t:page-template>
