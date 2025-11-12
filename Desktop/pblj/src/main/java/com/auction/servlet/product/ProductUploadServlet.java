package com.auction.servlet.product;

import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.impl.ProductService;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ProductUploadServlet extends BaseServlet {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("id");
        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                Optional<Product> productOpt = services().getProductService().findById(productId);
                productOpt.ifPresent(product -> req.setAttribute("product", product));
            } catch (SQLException e) {
                throw new ServletException("Unable to load product", e);
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/seller/product-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User seller = (User) session.getAttribute("user");
        ProductService productService = services().getProductService();

        Product product;
        try {
            int productId = Integer.parseInt(req.getParameter("id"));
            product = productService.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/seller/products");
            return;
        } catch (SQLException e) {
            throw new ServletException("Unable to load product", e);
        }

        if (product.getSellerId() != seller.getId()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        product.setTitle(req.getParameter("title"));
        product.setDescription(req.getParameter("description"));
        try {
            product.setBasePrice(new BigDecimal(req.getParameter("basePrice")));
            product.setBidIncrement(new BigDecimal(req.getParameter("bidIncrement")));
            product.setStartTime(LocalDateTime.parse(req.getParameter("startTime"), DATE_TIME_FORMATTER));
            product.setEndTime(LocalDateTime.parse(req.getParameter("endTime"), DATE_TIME_FORMATTER));
        } catch (Exception ex) {
            req.setAttribute("error", "Invalid product data");
            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/views/seller/product-form.jsp").forward(req, resp);
            return;
        }
        product.setImageUrl(req.getParameter("imageUrl"));

        try {
            productService.updateProduct(product);
            resp.sendRedirect(req.getContextPath() + "/seller/products");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/views/seller/product-form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to update product", e);
        }
    }
}


