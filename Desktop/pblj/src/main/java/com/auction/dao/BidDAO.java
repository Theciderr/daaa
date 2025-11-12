package com.auction.dao;

import com.auction.model.Bid;

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

public class BidDAO extends BaseDAO {

    public BidDAO(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Bid bid) throws SQLException {
        String sql = "INSERT INTO bids (product_id, buyer_id, bid_amount, bid_time) VALUES (?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bid.getProductId());
            ps.setInt(2, bid.getBuyerId());
            ps.setBigDecimal(3, bid.getBidAmount());
            ps.setTimestamp(4, Timestamp.valueOf(bid.getBidTime()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Unable to retrieve generated bid ID");
    }

    public Optional<Bid> findById(int bidId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bidId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<Bid> findHighestBid(int productId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE product_id=? ORDER BY bid_amount DESC, bid_time ASC LIMIT 1";
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

    public List<Bid> findByProduct(int productId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE product_id=? ORDER BY bid_amount DESC";
        List<Bid> bids = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bids.add(mapRow(rs));
                }
            }
        }
        return bids;
    }

    public List<Bid> findByBuyer(int buyerId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE buyer_id=? ORDER BY bid_time DESC";
        List<Bid> bids = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bids.add(mapRow(rs));
                }
            }
        }
        return bids;
    }

    public Optional<BigDecimal> findHighestBidAmount(int productId) throws SQLException {
        String sql = "SELECT MAX(bid_amount) AS highest FROM bids WHERE product_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal highest = rs.getBigDecimal("highest");
                    return Optional.ofNullable(highest);
                }
                return Optional.empty();
            }
        }
    }

    public Optional<Bid> findWinningBid(int productId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE product_id=? ORDER BY bid_amount DESC, bid_time ASC LIMIT 1";
        return findHighestBid(productId);
    }

    public void delete(int bidId) throws SQLException {
        String sql = "DELETE FROM bids WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bidId);
            ps.executeUpdate();
        }
    }

    private Bid mapRow(ResultSet rs) throws SQLException {
        Bid bid = new Bid();
        bid.setId(rs.getInt("id"));
        bid.setProductId(rs.getInt("product_id"));
        bid.setBuyerId(rs.getInt("buyer_id"));
        bid.setBidAmount(rs.getBigDecimal("bid_amount"));
        Timestamp bidTime = rs.getTimestamp("bid_time");
        if (bidTime != null) {
            bid.setBidTime(bidTime.toLocalDateTime());
        } else {
            bid.setBidTime(LocalDateTime.now());
        }
        return bid;
    }
}


