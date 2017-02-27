<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="language"/>

<fmt:message key="title.index" var="title"/>

<t:page-template title="${title}">
    <jsp:body>
        <fmt:message key="content.page.index"/>
    </jsp:body>
</t:page-template>