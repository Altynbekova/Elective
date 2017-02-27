<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.evaluate" var="title"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="studentId" value="${pageContext.request.getParameter('studentId')}"/>
<c:set var="courseId" value="${pageContext.request.getParameter('courseId')}"/>
<c:set var="registerError" value="${registerErrors}"/>

<t:page-template title="${title}">
    <jsp:body>
        <form action="${contextPath}/do/?action=evaluate-student&studentId=${studentId}&courseId=${courseId}"
              name="evaluateForm" method="post">
            <input type="hidden" name="studentId" value="studentId">
            <div class="form-group">
                <label for="grade"><fmt:message key="label.course.grade"/></label>
                <input type="text" class="form-control" id="grade" placeholder="<fmt:message key="placeholder.grade"/>"
                       name="grade" required>
                <span class="help-block"><fmt:message key="help.grade"/></span>
                <c:if test="${not empty gradeErrors}">
                    <t:error-messages errors="${gradeErrors}"/>
                </c:if>
            </div>
            <div class="form-group">
                <label for="feedback"><fmt:message key="label.feedback"/></label>
                <textarea class="form-control" rows="5" id="feedback" name="feedback" maxlength="255"></textarea>
                <c:if test="${not empty feedbackErrors}">
                    <t:error-messages errors="${feedbackErrors}"/>
                </c:if>
            </div>

            <button type="submit" class="btn btn-success"><fmt:message key="button.save"/></button>
        </form>
    </jsp:body>
</t:page-template>
