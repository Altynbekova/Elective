<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>
<c:set value="LECTURER" var="LECTURER"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:message key="title.student" var="title"/>
<t:page-template title="${title}">
    <jsp:body>
        <c:if test="${!completionInfo.isComplete()}">
            <p class="text-danger"><fmt:message key="label.course.incomplete.message"/></p>
        </c:if>
        <h4><fmt:message key="role.student"/></h4>
        <p>${student.firstName} ${student.lastName}</p><br>

        <h4><fmt:message key="label.course.grade"/></h4>
        <p>${completionInfo.grade}%</p><br>

        <h4><fmt:message key="label.feedback"/></h4>
            <p>${completionInfo.feedback}</p><br>


        <c:if test="${not completionInfo.isComplete() && sessionScope.user.role==LECTURER}">
            <hr>
            <a href="${contextPath}/do/?action=show-evaluate-student-form&studentId=${student.id}&courseId=${courseId}"
               class="btn btn-success"><fmt:message key="button.change"/></a>
        </c:if>
    </jsp:body>
</t:page-template>