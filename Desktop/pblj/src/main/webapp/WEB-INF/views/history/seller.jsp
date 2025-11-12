<c:set var="pageTitle" value="Sales History"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="history">
    <h2>Sales History &amp; Revenue</h2>
    <c:choose>
        <c:when test="${empty history}">
            <p>No sales recorded yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Final Price</th>
                    <th>Winner</th>
                    <th>Status</th>
                    <th>End Time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${history}" var="record">
                    <tr>
                        <td>${record.productTitle}</td>
                        <td>$${record.finalPrice}</td>
                        <td><c:out value="${empty record.winnerName ? '—' : record.winnerName}"/></td>
                        <td>${record.status}</td>
                        <td>${record.endTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

