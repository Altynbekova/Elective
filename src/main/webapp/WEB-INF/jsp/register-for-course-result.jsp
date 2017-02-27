<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.register.success" var="title" />
<c:set value="${pageContext.request.getParameter('result')}" var="result"/>
<c:set var="style" value="${result=='success'?'alert-success':'alert-danger'}"/>
<c:set value="${result=='success'?'message.register.for.course.success':'message.error.register.for.course.invalid'}"
       var="registerResultMsg"/>
<t:page-template title="${title}">
    <jsp:body>
        <p class="alert ${style}"><fmt:message key="${registerResultMsg}"/>
        </p>
    </jsp:body>
</t:page-template>


