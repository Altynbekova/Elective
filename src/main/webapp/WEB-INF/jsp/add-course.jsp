<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>
<fmt:message key="title.new.course" var="title"/>
<c:set value="${courseExistsError}" var="courseExistsError"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<t:page-template title="${title}">
    <c:if test="${not empty courseExistsError}">
        <div class="alert alert-danger alert-dismissable fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <fmt:message key="${courseExistsError}"/>
        </div>
    </c:if>

        <form name="addNewCourseForm" action="${contextPath}/do/?action=add-new-course" method="post">
            <div class="form-group">
                <label for="courseName"><fmt:message key="label.course.name"/></label>
                <input type="text" class="form-control" id="courseName"
                       placeholder="<fmt:message key="placeholder.course.name"/>"
                       name="name" minlength="3" maxlength="50" required>
                    <t:error-messages errors="${nameErrors}"/>
            </div>
            <div class="form-group">
                <label for="description"><fmt:message key="label.course.description"/></label>
                <textarea class="form-control" rows="5" id="description" name="description"
                          maxlength="255"></textarea>
                <c:if test="${not empty descriptionErrors}">
                    <t:error-messages errors="${descriptionErrors}"/>
                </c:if>
            </div>

            <button type="submit" class="btn btn-success"><fmt:message key="button.submit"/></button>
        </form>
</t:page-template>