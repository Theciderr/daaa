<c:set var="pageTitle" value="Login"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="auth-form">
    <h2>Login</h2>
    <c:if test="${not empty requestScope.error}">
        <div class="alert error">${requestScope.error}</div>
    </c:if>
    <c:if test="${not empty sessionScope.flash}">
        <div class="alert success">${sessionScope.flash}</div>
        <c:remove var="flash" scope="session"/>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
        <label>Email
            <input type="email" name="email" required/>
        </label>
        <label>Password
            <input type="password" name="password" required/>
        </label>
        <button type="submit" class="btn primary">Login</button>
    </form>
    <p>New here? <a href="${pageContext.request.contextPath}/register">Create an account</a>.</p>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

