<c:set var="pageTitle" value="Profile"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="profile">
    <h2>My Profile</h2>
    <c:if test="${not empty requestScope.success}">
        <div class="alert success">${requestScope.success}</div>
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <div class="alert error">${requestScope.error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/profile">
        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
        <label>Name
            <input type="text" name="name" value="${sessionScope.user.name}" required/>
        </label>
        <label>Email
            <input type="email" value="${sessionScope.user.email}" disabled/>
        </label>
        <label>Phone
            <input type="tel" name="phone" value="${sessionScope.user.phone}"/>
        </label>
        <label>Address
            <textarea name="address" rows="3">${sessionScope.user.address}</textarea>
        </label>
        <button type="submit" class="btn primary">Save Changes</button>
    </form>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

