<%@tag description="List of courses table" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<%@attribute name="courses" required="true" type="java.util.List" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${empty courses}">
    <p class="alert alert-danger"><fmt:message key="message.error.no.courses"/></p>
</c:if>
<c:if test="${not empty courses}">
    <div class="">
        <table class="table table-bordered table-condensed table-hover">
            <thead>
            <tr class="info">
                <th><fmt:message key="label.course.name"/></th>
                <th><fmt:message key="label.course.description"/></th>
                <th><fmt:message key="label.course.is.available"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courses}" var="course">
                <tr>
                    <td><a href="${contextPath}/do/?action=show-course-details&courseId=${course.id}">
                            ${course.name}
                    </a></td>
                    <td>${course.description}</td>
                    <td>
                        <c:if test="${course.isAvailable()}">
                            <span class="glyphicon glyphicon-ok text-success small" aria-hidden="true"></span>
                            <span class="sr-only"><fmt:message key="label.course.is.available"/></span>
                        </c:if>
                        <c:if test="${!course.isAvailable()}">
                            <span class="glyphicon glyphicon-remove text-danger small"></span>
                            <span class="sr-only"><fmt:message key="label.course.is.unavailable"/></span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>