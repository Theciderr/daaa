package com.auction.service.impl;

import com.auction.dao.ProductDAO;
import com.auction.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SearchService {

    private final ProductDAO productDAO;

    public SearchService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> search(String category, BigDecimal minPrice, BigDecimal maxPrice,
                                Integer sellerId, String sort, int offset, int limit) throws SQLException {
        return productDAO.search(category, minPrice, maxPrice, sellerId, sort, offset, limit);
    }
}


