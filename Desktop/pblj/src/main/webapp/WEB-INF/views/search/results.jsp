<c:set var="pageTitle" value="Browse Auctions"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="search">
    <h2>Browse Auctions</h2>
    <form class="search-filters" method="get" action="${pageContext.request.contextPath}/search">
        <input type="text" name="category" placeholder="Category" value="${param.category}"/>
        <input type="number" name="minPrice" placeholder="Min price" value="${param.minPrice}" step="0.01" min="0"/>
        <input type="number" name="maxPrice" placeholder="Max price" value="${param.maxPrice}" step="0.01" min="0"/>
        <input type="number" name="sellerId" placeholder="Seller ID" value="${param.sellerId}" min="1"/>
        <select name="sort">
            <option value="">Sort by</option>
            <option value="ending" ${param.sort eq 'ending' ? 'selected' : ''}>Ending soon</option>
            <option value="highest" ${param.sort eq 'highest' ? 'selected' : ''}>Highest bid</option>
        </select>
        <button type="submit" class="btn primary">Apply</button>
    </form>
    <div class="auction-cards">
        <c:forEach items="${products}" var="product">
            <article class="auction-card" data-product-id="${product.id}">
                <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.title}"/>
                <h3>${product.title}</h3>
                <p>${product.description}</p>
                <p><strong>Current Bid:</strong>
                    <c:out value="${product.currentBid != null ? product.currentBid : product.basePrice}"/></p>
                <p><strong>Ends:</strong> ${product.endTime}</p>
                <a class="btn secondary" href="${pageContext.request.contextPath}/search?productId=${product.id}">Open Auction</a>
            </article>
        </c:forEach>
    </div>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

