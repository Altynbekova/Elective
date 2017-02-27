<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.course" var="title"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="courseErrorMessage" value="${courseErrorMessages}"/>
<c:set var="STUDENT" value="STUDENT"/>
<c:set var="LECTURER" value="LECTURER"/>

<t:page-template title="${title}">
    <jsp:body>
        <c:if test="${not empty courseErrorMessage}">
            <p class="alert alert-danger"><fmt:message key="${courseErrorMessage}"/></p>
        </c:if>
        <ul class="nav nav-tabs">
            <li class="active">
                <a data-toggle="tab" href="#about"><fmt:message key="content.course.about"/></a>
            </li>
            <c:if test="${!(sessionScope.user.role==LECTURER)}">
                <li><a data-toggle="tab" href="#join"><fmt:message key="content.course.join.header"/></a></li>
            </c:if>
            <c:if test="${sessionScope.user.role==LECTURER && course.lecturer.id==sessionScope.user.id}">
                <li><a data-toggle="tab" href="#availability"><fmt:message key="content.course.status.header"/></a></li>

                <li>
                    <a data-toggle="tab" href="#studList"><fmt:message
                            key="content.course.students.list.header"/></a>
                </li>

            </c:if>
        </ul>

        <div class="tab-content">
            <!--About course-->
            <div id="about" class="tab-pane fade in active">
                <h4><fmt:message key="label.course.name"/></h4>
                <p>${course.name}</p><br>
                <h4><fmt:message key="role.lecturer"/></h4>
                <p>${course.lecturer.firstName} ${course.lecturer.lastName}</p>
                <c:if test="${not empty course.lecturer.jobTitle}">
                    <p>${course.lecturer.jobTitle}</p>
                </c:if><br>
                <c:if test="${not empty course.description}">
                    <h4><fmt:message key="label.course.description"/></h4>
                    <p>${course.name}</p><br>
                </c:if>
            </div>

            <!--Registration for course-->
            <c:if test="${!(sessionScope.user.role==LECTURER)}">
                <div id="join" class="tab-pane fade">
                    <p><fmt:message key="content.course.join"/></p><br>

                    <c:if test="${sessionScope.user.role==STUDENT && course.isAvailable()}">
                        <hr>
                        <a href="${contextPath}/do/?action=register-for-course&courseId=${course.id}"
                           class="btn btn-success" role="button">
                            <fmt:message key="button.register"/>
                        </a>
                    </c:if>
                </div>
            </c:if>

            <!--Course status-->
            <c:if test="${sessionScope.user.role==LECTURER}">
                <div id="availability" class="tab-pane fade">
                    <p><fmt:message key="content.course.status"/></p>
                    <c:set value="${course.isAvailable()?'disabled':''}" var="enabled"/>
                    <c:set value="${course.isAvailable()?'':'disabled'}" var="disabled"/>
                    <c:set value="${course.isAvailable()?'':'required'}" var="requiredAvailable"/>
                    <c:set value="${course.isAvailable()?'required':''}" var="requiredUnavailable"/>

                    <form action="${contextPath}/do/?action=open-registration&courseId=${course.id}" method="post">
                        <p><fmt:message key="content.course.status.change"/></p>
                        <div class="radio ${enabled}">
                            <label><input type="radio" name="status" value="true" ${enabled} ${requiredAvailable}>
                                <fmt:message key="label.course.is.available"/></label>
                        </div>
                        <div class="radio ${disabled}">
                            <label><input type="radio" name="status" value="false" ${disabled} ${requiredUnavailable}>
                                <fmt:message key="label.course.is.unavailable"/></label>
                        </div>

                        <button class="btn btn-success btn-sm" type="submit">
                            <fmt:message key="button.submit"/></button>
                    </form>
                </div>

                <!--List of students registered for course-->
                <div id="studList" class="tab-pane fade">
                    <a href="${contextPath}/do/?action=show-students&courseId=${course.id}">
                        <fmt:message key="content.course.students.list"/></a>
                </div>
            </c:if>

        </div>
    </jsp:body>
</t:page-template>
