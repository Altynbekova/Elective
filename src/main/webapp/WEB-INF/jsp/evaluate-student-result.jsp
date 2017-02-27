<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.evaluate.result" var="title"/>
<c:set value="${pageContext.request.getParameter('result')}" var="result"/>"
<t:page-template title="${title}">
    <jsp:body>
        <c:if test="${result=='success'}">
            <p class="alert alert-success"><fmt:message key="message.evaluate.student.success"/>
                <a href="${pageContext.request.contextPath}/do/?action=show-student&studentId=${sessionScope.get('studentId')}&courseId=${sessionScope.get('courseId')}">
                    <fmt:message key="page"/>
                </a>
            </p>
        </c:if>
    </jsp:body>
</t:page-template>