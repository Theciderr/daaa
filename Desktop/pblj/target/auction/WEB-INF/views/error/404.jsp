<c:set var="pageTitle" value="Page Not Found"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="error-page">
    <h2>404 - Page Not Found</h2>
    <p>The page you are looking for might have been removed or is temporarily unavailable.</p>
    <a class="btn primary" href="${pageContext.request.contextPath}/">Return Home</a>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

