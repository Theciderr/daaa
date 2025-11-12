<c:set var="pageTitle" value="Register"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="auth-form">
    <h2>Create Account</h2>
    <c:if test="${not empty requestScope.error}">
        <div class="alert error">${requestScope.error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
        <label>Name
            <input type="text" name="name" value="${param.name}" required/>
        </label>
        <label>Email
            <input type="email" name="email" value="${param.email}" required/>
        </label>
        <label>Password
            <input type="password" name="password" required minlength="6"/>
        </label>
        <label>Role
            <select name="role" required>
                <option value="">Select role</option>
                <option value="buyer" ${param.role eq 'buyer' ? 'selected' : ''}>Buyer</option>
                <option value="seller" ${param.role eq 'seller' ? 'selected' : ''}>Seller</option>
            </select>
        </label>
        <label>Phone
            <input type="tel" name="phone" value="${param.phone}"/>
        </label>
        <label>Address
            <textarea name="address" rows="3">${param.address}</textarea>
        </label>
        <button type="submit" class="btn primary">Register</button>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a>.</p>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

