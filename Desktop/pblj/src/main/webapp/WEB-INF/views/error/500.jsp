<c:set var="pageTitle" value="Server Error"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="error-page">
    <h2>500 - Internal Server Error</h2>
    <p>Something went wrong. Please try again later or contact support.</p>
    <a class="btn primary" href="${pageContext.request.contextPath}/">Return Home</a>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

