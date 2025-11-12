package com.auction.dao;

import com.auction.model.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO extends BaseDAO {

    public ProductDAO(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Product product) throws SQLException {
        String sql = "INSERT INTO products (seller_id, title, description, base_price, current_bid, bid_increment, start_time, end_time, status, image_url) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product.getSellerId());
            ps.setString(2, product.getTitle());
            ps.setString(3, product.getDescription());
            ps.setBigDecimal(4, product.getBasePrice());
            ps.setBigDecimal(5, product.getCurrentBid());
            ps.setBigDecimal(6, product.getBidIncrement());
            ps.setTimestamp(7, Timestamp.valueOf(product.getStartTime()));
            ps.setTimestamp(8, Timestamp.valueOf(product.getEndTime()));
            ps.setString(9, product.getStatus());
            ps.setString(10, product.getImageUrl());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Unable to retrieve generated product ID");
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE products SET title=?, description=?, base_price=?, bid_increment=?, start_time=?, end_time=?, status=?, image_url=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getBasePrice());
            ps.setBigDecimal(4, product.getBidIncrement());
            ps.setTimestamp(5, Timestamp.valueOf(product.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(product.getEndTime()));
            ps.setString(7, product.getStatus());
            ps.setString(8, product.getImageUrl());
            ps.setInt(9, product.getId());
            ps.executeUpdate();
        }
    }

    public void updateStatus(int productId, String status) throws SQLException {
        String sql = "UPDATE products SET status=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public void updateCurrentBid(int productId, BigDecimal bidAmount) throws SQLException {
        String sql = "UPDATE products SET current_bid=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (bidAmount != null) {
                ps.setBigDecimal(1, bidAmount);
            } else {
                ps.setNull(1, java.sql.Types.DECIMAL);
            }
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public Optional<Product> findById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    public List<Product> findActiveAuctions(LocalDateTime now) throws SQLException {
        String sql = "SELECT * FROM products WHERE status IN ('scheduled','active') AND end_time >= ? ORDER BY end_time ASC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(now));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        }
        return products;
    }

    public List<Product> findBySeller(int sellerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE seller_id=? ORDER BY start_time DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        }
        return products;
    }

    public List<Product> search(String category, BigDecimal minPrice, BigDecimal maxPrice, Integer sellerId,
                                String sort, int offset, int limit) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE status IN ('active','scheduled')");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.isEmpty()) {
            sql.append(" AND description LIKE ?");
            params.add("Category:" + category + "%");
        }
        if (minPrice != null) {
            sql.append(" AND (COALESCE(current_bid, base_price) >= ?)");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND (COALESCE(current_bid, base_price) <= ?)");
            params.add(maxPrice);
        }
        if (sellerId != null) {
            sql.append(" AND seller_id = ?");
            params.add(sellerId);
        }

        if ("ending".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY end_time ASC");
        } else if ("highest".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY COALESCE(current_bid, base_price) DESC");
        } else {
            sql.append(" ORDER BY start_time DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof BigDecimal) {
                    ps.setBigDecimal(i + 1, (BigDecimal) param);
                } else if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else {
                    ps.setString(i + 1, param.toString());
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        }
        return products;
    }

    public List<Product> findExpiredAuctions(LocalDateTime threshold) throws SQLException {
        String sql = "SELECT * FROM products WHERE status IN ('active','scheduled') AND end_time <= ?";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(threshold));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        }
        return products;
    }

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM products ORDER BY end_time DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        }
        return products;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setSellerId(rs.getInt("seller_id"));
        product.setTitle(rs.getString("title"));
        product.setDescription(rs.getString("description"));
        product.setBasePrice(rs.getBigDecimal("base_price"));
        product.setCurrentBid(rs.getBigDecimal("current_bid"));
        product.setBidIncrement(rs.getBigDecimal("bid_increment"));
        Timestamp startT = rs.getTimestamp("start_time");
        if (startT != null) {
            product.setStartTime(startT.toLocalDateTime());
        }
        Timestamp endT = rs.getTimestamp("end_time");
        if (endT != null) {
            product.setEndTime(endT.toLocalDateTime());
        }
        product.setStatus(rs.getString("status"));
        product.setImageUrl(rs.getString("image_url"));
        return product;
    }
}


