package com.auction.service.impl;

import com.auction.dao.ProductDAO;
import com.auction.model.Product;
import com.auction.util.ValidationUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductService {

    public static final String STATUS_SCHEDULED = "scheduled";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_CLOSED = "closed";
    public static final String STATUS_CANCELLED = "cancelled";

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product createProduct(Product product) throws SQLException {
        validateProduct(product);
        product.setStatus(determineInitialStatus(product.getStartTime()));
        int id = productDAO.create(product);
        product.setId(id);
        return product;
    }

    public void updateProduct(Product product) throws SQLException {
        validateProduct(product);
        productDAO.update(product);
    }

    public Optional<Product> findById(int id) throws SQLException {
        return productDAO.findById(id);
    }

    public List<Product> findActiveAuctions(LocalDateTime now) throws SQLException {
        return productDAO.findActiveAuctions(now);
    }

    public List<Product> findExpiredAuctions(LocalDateTime threshold) throws SQLException {
        return productDAO.findExpiredAuctions(threshold);
    }

    public List<Product> findBySeller(int sellerId) throws SQLException {
        return productDAO.findBySeller(sellerId);
    }

    public List<Product> search(String category, BigDecimal minPrice, BigDecimal maxPrice, Integer sellerId,
                                String sort, int offset, int limit) throws SQLException {
        return productDAO.search(category, minPrice, maxPrice, sellerId, sort, offset, limit);
    }

    public void updateStatus(int productId, String status) throws SQLException {
        productDAO.updateStatus(productId, status);
    }

    public void updateCurrentBid(int productId, BigDecimal bidAmount) throws SQLException {
        productDAO.updateCurrentBid(productId, bidAmount);
    }

    private void validateProduct(Product product) {
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (!ValidationUtil.isPositive(product.getBasePrice())) {
            throw new IllegalArgumentException("Base price must be positive");
        }
        if (!ValidationUtil.isPositive(product.getBidIncrement())) {
            throw new IllegalArgumentException("Bid increment must be positive");
        }
        if (!ValidationUtil.isEndAfterStart(product.getStartTime(), product.getEndTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    private String determineInitialStatus(LocalDateTime startTime) {
        LocalDateTime now = LocalDateTime.now();
        return startTime.isAfter(now) ? STATUS_SCHEDULED : STATUS_ACTIVE;
    }
}


