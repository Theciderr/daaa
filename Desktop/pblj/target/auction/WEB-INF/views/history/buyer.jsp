<c:set var="pageTitle" value="My Bid History"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="history">
    <h2>Bid History</h2>
    <c:choose>
        <c:when test="${empty history}">
            <p>You have not placed any bids yet.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Bid Amount</th>
                    <th>Status</th>
                    <th>Auction End</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${history}" var="record">
                    <tr>
                        <td>${record.productTitle}</td>
                        <td>$${record.bidAmount}</td>
                        <td>
                            <span class="badge ${record.won ? 'success' : 'muted'}">
                                <c:out value="${record.won ? 'Won' : 'Lost'}"/>
                            </span>
                        </td>
                        <td>${record.auctionEndTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

