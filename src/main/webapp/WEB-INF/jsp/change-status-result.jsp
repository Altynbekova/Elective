<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.status.updated" var="title"/>
<c:set value="${pageContext.request.getParameter('result')}" var="result"/>
<c:set var="style" value="${result=='success'?'alert-success':'alert-danger'}"/>
<t:page-template title="${title}">
    <jsp:body>
        <p class="alert ${style}">
            <c:if test="${result=='success'}"><fmt:message key="message.change.course.status.success"/></c:if>
            <c:if test="${result=='error'}"><fmt:message key="message.error.change.course.status.invalid"/></c:if>
        </p>
    </jsp:body>
</t:page-template>