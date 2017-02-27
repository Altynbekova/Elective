<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.search.result" var="title"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set value="${pageContext.request.getAttribute('result')}" var="result"/>
<c:set var="foundCoursesError" value="${foundCoursesError}"/>

<t:page-template title="${title}">
    <jsp:body>
    <c:if test="${result=='error'}">
        <p class="alert alert-danger"><fmt:message key="${foundCoursesError}"/></p>
    </c:if>
    <t:courses-table courses="${pageContext.session.getAttribute('courses')}"/>
    </jsp:body>
</t:page-template>