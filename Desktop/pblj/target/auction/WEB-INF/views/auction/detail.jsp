<c:set var="pageTitle" value="${product.title}"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="auction-detail" data-product-id="${product.id}">
    <header>
        <h2>${product.title}</h2>
        <p>${product.description}</p>
    </header>
    <div class="auction-main">
        <div class="auction-image">
            <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.title}"/>
        </div>
        <div class="auction-info">
            <p><strong>Status:</strong> <span id="auctionStatus">${product.status}</span></p>
            <p><strong>Current Bid:</strong> $<span id="currentBid"><c:out value="${highestBid.bidAmount != null ? highestBid.bidAmount : product.basePrice}"/></span></p>
            <p><strong>Highest Bidder:</strong> <span id="highestBidder"><c:out value="${highestBidBidder}"/></span></p>
            <p><strong>Time Left:</strong> <span id="timeLeft"></span></p>
            <c:if test="${sessionScope.user.role eq 'buyer'}">
                <form id="bidForm">
                    <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <label>Your Bid
                        <input type="number" name="bidAmount" step="0.01" min="0" required/>
                    </label>
                    <button type="submit" class="btn primary">Place Bid</button>
                </form>
                <div id="bidMessage" class="alert hidden"></div>
            </c:if>
        </div>
    </div>
    <section class="bid-history">
        <h3>Bid History</h3>
        <table class="data-table">
            <thead>
            <tr>
                <th>Bidder</th>
                <th>Amount</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody id="bidHistory">
            <c:forEach items="${bids}" var="bid">
                <tr>
                    <td>${bid.bidderName}</td>
                    <td>$${bid.amount}</td>
                    <td>${bid.time}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</section>
<script defer>
    const productId = document.querySelector('.auction-detail').dataset.productId;
    const feedUrl = `${window.appContext.baseUrl}/auction/feed?productId=${productId}`;
    const bidForm = document.getElementById('bidForm');
    const bidMessage = document.getElementById('bidMessage');

    async function fetchAuctionFeed() {
        const response = await fetch(feedUrl, {headers: {"X-Requested-With": "XMLHttpRequest"}});
        const xmlText = await response.text();
        const doc = new DOMParser().parseFromString(xmlText, "application/xml");
        if (doc.querySelector("status")?.textContent !== "success") {
            return;
        }
        const currentBid = doc.querySelector("highestBid amount")?.textContent
            || doc.querySelector("details currentBid")?.textContent;
        if (currentBid) {
            document.getElementById("currentBid").textContent = currentBid;
        }
        const bidder = doc.querySelector("highestBid bidder")?.textContent || "—";
        document.getElementById("highestBidder").textContent = bidder;
        document.getElementById("auctionStatus").textContent = doc.querySelector("details status").textContent;
        document.getElementById("timeLeft").textContent = `${doc.querySelector("details timeLeftSeconds").textContent} seconds`;

        const historyRows = doc.querySelectorAll("recentBids bid");
        const tbody = document.getElementById("bidHistory");
        tbody.innerHTML = "";
        historyRows.forEach(row => {
            const bidderName = row.querySelector("bidder")?.textContent || "Anonymous";
            const amount = row.querySelector("amount")?.textContent;
            const time = row.querySelector("time")?.textContent;
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${bidderName}</td><td>$${amount}</td><td>${time}</td>`;
            tbody.appendChild(tr);
        });
    }

    if (bidForm) {
        bidForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const formData = new FormData(bidForm);
            formData.append("_csrf", window.appContext.csrfToken);
            const response = await fetch(`${window.appContext.baseUrl}/bid`, {
                method: "POST",
                headers: {"X-Requested-With": "XMLHttpRequest", "X-CSRF-Token": window.appContext.csrfToken},
                body: formData
            });
            const doc = new DOMParser().parseFromString(await response.text(), "application/xml");
            const status = doc.querySelector("status")?.textContent;
            const message = doc.querySelector("message")?.textContent;
            bidMessage.classList.remove("hidden", "error", "success");
            bidMessage.classList.add(status === "success" ? "success" : "error");
            bidMessage.textContent = message || (status === "success" ? "Bid placed!" : "Unable to place bid.");
            fetchAuctionFeed();
        });
    }

    fetchAuctionFeed();
    setInterval(fetchAuctionFeed, 3000);
</script>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

