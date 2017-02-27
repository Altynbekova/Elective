<%@tag description="Error messages" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="language"/>

<%@attribute name="errors" required="true" type="java.util.List" %>
<div class="text text-danger">
    <c:forEach items="${errors}" var="error">
        <p><fmt:message key="${error}"/></p>
    </c:forEach>
</div>