<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<c:set var="pageTitle" value="Welcome to AuctionHub"/>
<section class="hero">
    <h2>Bid, Win, and Sell with Confidence</h2>
    <p>Join a secure, real-time marketplace for buyers and sellers.</p>
    <div class="cta-buttons">
        <a class="btn primary" href="${pageContext.request.contextPath}/search">Browse Auctions</a>
        <a class="btn secondary" href="${pageContext.request.contextPath}/register">Get Started</a>
    </div>
</section>
<section class="features">
    <article>
        <h3>Live Updates</h3>
        <p>Real-time XML feeds keep you informed of every bid as it happens.</p>
    </article>
    <article>
        <h3>Secure Payments</h3>
        <p>Hold funds in escrow until the auction is complete and confirmed.</p>
    </article>
    <article>
        <h3>Powerful Tools</h3>
        <p>Advanced search, admin controls, and comprehensive history reports.</p>
    </article>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

