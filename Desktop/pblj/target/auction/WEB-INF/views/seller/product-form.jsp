<c:set var="pageTitle" value="${empty product ? 'Create Listing' : 'Edit Listing'}"/>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<section class="seller-product-form">
    <h2><c:out value="${pageTitle}"/></h2>
    <c:if test="${not empty requestScope.error}">
        <div class="alert error">${requestScope.error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}${empty product ? '/seller/products' : '/seller/products/upload'}">
        <input type="hidden" name="_csrf" value="${sessionScope.csrfToken}"/>
        <c:if test="${not empty product}">
            <input type="hidden" name="id" value="${product.id}"/>
        </c:if>
        <label>Title
            <input type="text" name="title" value="${product.title}" required/>
        </label>
        <label>Description
            <textarea name="description" rows="4">${product.description}</textarea>
        </label>
        <label>Base Price
            <input type="number" name="basePrice" min="1" step="0.01" value="${product.basePrice}" required/>
        </label>
        <label>Bid Increment
            <input type="number" name="bidIncrement" min="1" step="0.01" value="${product.bidIncrement}" required/>
        </label>
        <label>Start Time
            <input type="datetime-local" name="startTime"
                   value="<fmt:formatDate value='${product.startTime}' pattern='yyyy-MM-dd''T''HH:mm'/>" required/>
        </label>
        <label>End Time
            <input type="datetime-local" name="endTime"
                   value="<fmt:formatDate value='${product.endTime}' pattern='yyyy-MM-dd''T''HH:mm'/>" required/>
        </label>
        <div class="image-upload">
            <label>Image
                <input type="hidden" name="imageUrl" id="imageUrl" value="${product.imageUrl}"/>
                <input type="file" id="imageFile" accept="image/*"/>
            </label>
            <c:if test="${not empty product.imageUrl}">
                <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.title}" class="preview"/>
            </c:if>
        </div>
        <button type="submit" class="btn primary">Save Listing</button>
    </form>
</section>
<script defer>
    document.addEventListener('DOMContentLoaded', function () {
        const fileInput = document.getElementById('imageFile');
        if (!fileInput) return;
        fileInput.addEventListener('change', async function () {
            if (!fileInput.files.length) return;
            const productIdField = document.querySelector('input[name="id"]');
            const formData = new FormData();
            formData.append("image", fileInput.files[0]);
            if (productIdField) {
                formData.append("productId", productIdField.value);
            } else {
                alert("Save the product first to upload images.");
                fileInput.value = "";
                return;
            }
            formData.append("_csrf", window.appContext.csrfToken);
            const response = await fetch(`${window.appContext.baseUrl}/seller/products/image`, {
                method: "POST",
                headers: {
                    "X-CSRF-Token": window.appContext.csrfToken
                },
                body: formData
            });
            const xml = await response.text();
            const parser = new DOMParser();
            const doc = parser.parseFromString(xml, "application/xml");
            if (doc.querySelector("status")?.textContent === "success") {
                const imageUrl = doc.querySelector("imageUrl").textContent;
                document.getElementById("imageUrl").value = imageUrl;
            } else {
                alert(doc.querySelector("message")?.textContent || "Upload failed");
            }
        });
    });
</script>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

