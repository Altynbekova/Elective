<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.students" var="title"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="studentsNotFound" value="${studentsNotFound}"/>

<t:page-template title="${title}">
    <c:if test="${not empty studentsNotFound}">
        <p class="alert alert-danger"><fmt:message key="${studentsNotFound}"/></p>
    </c:if>

    <c:if test="${not empty students}">
            <ul>
                <c:forEach var="student" items="${students}">
                    <li>
                        <a href="${contextPath}/do/?action=show-student&studentId=${student.id}&courseId=${courseId}">
                            <p>${student.lastName} ${student.firstName}</p>
                        </a>
                    </li>
                </c:forEach>
            </ul>
    </c:if>
</t:page-template>
