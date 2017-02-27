<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="language"/>

<fmt:message key="title.error.403" var="title"/>
<t:page-template title="${title}">
    <jsp:body>
        <t:error-page-container heading="content.error.403.header" body="content.error.403"/>
    </jsp:body>
</t:page-template>