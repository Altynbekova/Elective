<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.lecturer.courses" var="title"/>
<c:set var="coursesNotFoundMsg" value="${coursesError}"/>

<t:page-template title="${title}">
    <jsp:body>
        <c:if test="${not empty pageContext.request.getAttribute('addCourseSuccess')}">
            <p class="alert alert-success"><fmt:message key="${pageContext.request.getAttribute('addCourseSuccess')}"/></p>
        </c:if>
       <t:courses-table courses="${courses}"/>
    </jsp:body>
</t:page-template>

