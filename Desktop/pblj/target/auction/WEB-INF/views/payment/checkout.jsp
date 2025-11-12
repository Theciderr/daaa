<c:set var="pageTitle" value="Payment Center"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="payment">
    <h2>Secure Payment</h2>
    <c:choose>
        <c:when test="${empty payment}">
            <p>Select a winning bid to manage payment status.</p>
        </c:when>
        <c:otherwise>
            <div class="payment-card">
                <p><strong>Transaction:</strong> ${payment.transactionId}</p>
                <p><strong>Amount:</strong> $${payment.amount}</p>
                <p><strong>Status:</strong> ${payment.status}</p>
            </div>
            <div class="payment-actions">
                <form id="paymentReleaseForm">
                    <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
                    <input type="hidden" name="bidId" value="${payment.bidId}"/>
                    <button type="submit" name="action" value="release" class="btn primary">Release Payment</button>
                    <button type="submit" name="action" value="refund" class="btn secondary">Refund Payment</button>
                </form>
            </div>
            <div id="paymentMessage" class="alert hidden"></div>
        </c:otherwise>
    </c:choose>
</section>
<script defer>
    const paymentForm = document.getElementById('paymentReleaseForm');
    if (paymentForm) {
        paymentForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const submitter = event.submitter;
            const formData = new URLSearchParams(new FormData(paymentForm));
            formData.append("action", submitter.value);
            formData.append("_csrf", window.appContext.csrfToken);
            const response = await fetch(`${window.appContext.baseUrl}/payment`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-CSRF-Token": window.appContext.csrfToken
                },
                body: formData.toString()
            });
            const xml = new DOMParser().parseFromString(await response.text(), "application/xml");
            const message = xml.querySelector("message")?.textContent || "Action complete.";
            const status = xml.querySelector("status")?.textContent;
            const alertBox = document.getElementById("paymentMessage");
            alertBox.classList.remove("hidden", "error", "success");
            alertBox.classList.add(status === "success" ? "success" : "error");
            alertBox.textContent = message;
        });
    }
</script>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

