<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.my.courses" var="title"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<t:page-template title="${title}">
    <jsp:body>
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
                        <th><fmt:message key="label.course.is.complete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${courses}" var="courseEntry">
                        <tr>
                            <td>
                                <a href="${contextPath}/do/?action=show-student&studentId=${sessionScope.user.id}&courseId=${courseEntry.key.id}">
                                        ${courseEntry.key.name}
                                </a></td>
                            <td>${courseEntry.key.description}</td>
                            <td class="small">
                                <c:if test="${isComplete}">
                                    <span class="glyphicon glyphicon-ok text-success small"></span>
                                </c:if>
                                <c:if test="${not isComplete}">
                                    <span class="glyphicon glyphicon-remove text-danger small"></span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </jsp:body>

</t:page-template>