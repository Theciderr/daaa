<c:set var="pageTitle" value="Admin Dashboard"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="admin-dashboard">
    <h2>Platform Overview</h2>
    <div class="stats">
        <div class="stat-card">
            <h3>Total Revenue</h3>
            <p>$<c:out value="${revenue}"/></p>
        </div>
    </div>
    <section class="admin-auctions">
        <h3>All Auctions</h3>
        <table class="data-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Seller</th>
                <th>Status</th>
                <th>Current Bid</th>
                <th>Ends</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${auctions}" var="auction">
                <tr data-product-id="${auction.id}">
                    <td>${auction.id}</td>
                    <td>${auction.title}</td>
                    <td>${auction.sellerId}</td>
                    <td>${auction.status}</td>
                    <td><c:out value="${auction.currentBid != null ? auction.currentBid : auction.basePrice}"/></td>
                    <td>${auction.endTime}</td>
                    <td>
                        <button class="btn danger small" data-action="cancelAuction">Cancel</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
    <section class="admin-disputes">
        <h3>Cancel Bid</h3>
        <form id="cancelBidForm" class="inline-form">
            <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
            <label>Bid ID
                <input type="number" name="bidId" min="1" required/>
            </label>
            <label>Product ID
                <input type="number" name="productId" min="1" required/>
            </label>
            <button type="submit" class="btn danger">Cancel Bid</button>
        </form>
        <div id="adminMessage" class="alert hidden"></div>
    </section>
</section>
<script defer>
    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('[data-action="cancelAuction"]').forEach(button => {
            button.addEventListener('click', async () => {
                const row = button.closest('tr');
                const productId = row.dataset.productId;
                if (!confirm(`Cancel auction ${productId}?`)) {
                    return;
                }
                const formData = new URLSearchParams();
                formData.append("action", "cancelAuction");
                formData.append("productId", productId);
                formData.append("_csrf", window.appContext.csrfToken);
                const response = await fetch(`${window.appContext.baseUrl}/admin/dispute`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-CSRF-Token": window.appContext.csrfToken
                    },
                    body: formData.toString()
                });
                const xml = new DOMParser().parseFromString(await response.text(), "application/xml");
                if (xml.querySelector("status")?.textContent === "success") {
                    row.querySelector('td:nth-child(4)').textContent = "cancelled";
                } else {
                    alert(xml.querySelector("message")?.textContent || "Action failed");
                }
            });
        });
        const bidForm = document.getElementById('cancelBidForm');
        const messageBox = document.getElementById('adminMessage');
        bidForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new URLSearchParams(new FormData(bidForm));
            formData.append("action", "cancelBid");
            formData.append("_csrf", window.appContext.csrfToken);
            const response = await fetch(`${window.appContext.baseUrl}/admin/dispute`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-CSRF-Token": window.appContext.csrfToken
                },
                body: formData.toString()
            });
            const xml = new DOMParser().parseFromString(await response.text(), "application/xml");
            const status = xml.querySelector("status")?.textContent;
            const message = xml.querySelector("message")?.textContent || "Action complete";
            messageBox.classList.remove("hidden", "success", "error");
            messageBox.classList.add(status === "success" ? "success" : "error");
            messageBox.textContent = message;
        });
    });
</script>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

