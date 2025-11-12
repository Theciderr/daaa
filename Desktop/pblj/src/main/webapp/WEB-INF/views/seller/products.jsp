<c:set var="pageTitle" value="My Listings"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="seller-products">
    <header class="section-header">
        <h2>My Auctions</h2>
        <a class="btn primary" href="${pageContext.request.contextPath}/seller/products/upload">Create Listing</a>
    </header>
    <c:choose>
        <c:when test="${empty products}">
            <p>You have no products listed yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Base Price</th>
                    <th>Current Bid</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr>
                        <td>${product.title}</td>
                        <td class="status ${product.status}">${product.status}</td>
                        <td>${product.basePrice}</td>
                        <td><c:out value="${product.currentBid != null ? product.currentBid : '—'}"/></td>
                        <td>${product.startTime}</td>
                        <td>${product.endTime}</td>
                        <td>
                            <a class="btn small" href="${pageContext.request.contextPath}/seller/products/upload?id=${product.id}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

