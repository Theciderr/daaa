package com.auction.dao;

import com.auction.model.Payment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class PaymentDAO extends BaseDAO {

    public PaymentDAO(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (bid_id, amount, status, transaction_id, paid_at) VALUES (?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getBidId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setString(3, payment.getStatus());
            ps.setString(4, payment.getTransactionId());
            if (payment.getPaidAt() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(payment.getPaidAt()));
            } else {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Unable to retrieve generated payment ID");
    }

    public Optional<Payment> findByBidId(int bidId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE bid_id=?";
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

    public void updateStatus(int paymentId, String status, LocalDateTime paidAt) throws SQLException {
        String sql = "UPDATE payments SET status=?, paid_at=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            if (paidAt != null) {
                ps.setTimestamp(2, Timestamp.valueOf(paidAt));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }
            ps.setInt(3, paymentId);
            ps.executeUpdate();
        }
    }

    public java.math.BigDecimal calculateTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM payments WHERE status IN ('HELD','RELEASED')";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal("total") != null ? rs.getBigDecimal("total") : java.math.BigDecimal.ZERO;
            }
            return java.math.BigDecimal.ZERO;
        }
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setBidId(rs.getInt("bid_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setStatus(rs.getString("status"));
        payment.setTransactionId(rs.getString("transaction_id"));
        Timestamp paidAt = rs.getTimestamp("paid_at");
        if (paidAt != null) {
            payment.setPaidAt(paidAt.toLocalDateTime());
        }
        return payment;
    }
}


