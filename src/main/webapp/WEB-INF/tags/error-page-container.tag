<%@tag description="Container for error pages" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>
<%@attribute name="heading" required="true" %>
<%@attribute name="body" required="true" %>

<div class="container-fluid">
    <div class="panel panel-danger">
        <div class="panel-heading">
            <h3>
                <fmt:message key="content.error.code"/> ${pageContext.errorData.statusCode}<br>
                <fmt:message key="${heading}"/>
            </h3>
        </div>
        <div class="panel-body">
            <p><fmt:message key="${body}"/></p>
        </div>
    </div>
</div>