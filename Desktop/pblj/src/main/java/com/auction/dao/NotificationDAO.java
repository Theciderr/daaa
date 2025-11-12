package com.auction.dao;

import com.auction.model.Notification;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO extends BaseDAO {

    public NotificationDAO(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, product_id, message, type, is_read, created_at) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, notification.getUserId());
            if (notification.getProductId() != null) {
                ps.setInt(2, notification.getProductId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setString(3, notification.getMessage());
            ps.setString(4, notification.getType());
            ps.setBoolean(5, notification.isRead());
            ps.setTimestamp(6, Timestamp.valueOf(notification.getCreatedAt()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Unable to retrieve generated notification ID");
    }

    public List<Notification> findUnreadByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE user_id=? AND is_read = 0 ORDER BY created_at DESC";
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapRow(rs));
                }
            }
        }
        return notifications;
    }

    public void markAsRead(List<Integer> notificationIds) throws SQLException {
        if (notificationIds == null || notificationIds.isEmpty()) {
            return;
        }
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < notificationIds.size(); i++) {
            placeholders.append("?");
            if (i < notificationIds.size() - 1) {
                placeholders.append(",");
            }
        }
        String sql = "UPDATE notifications SET is_read = 1 WHERE id IN (" + placeholders + ")";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < notificationIds.size(); i++) {
                ps.setInt(i + 1, notificationIds.get(i));
            }
            ps.executeUpdate();
        }
    }

    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getInt("id"));
        notification.setUserId(rs.getInt("user_id"));
        int productId = rs.getInt("product_id");
        if (!rs.wasNull()) {
            notification.setProductId(productId);
        }
        notification.setMessage(rs.getString("message"));
        notification.setType(rs.getString("type"));
        notification.setRead(rs.getBoolean("is_read"));
        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            notification.setCreatedAt(created.toLocalDateTime());
        } else {
            notification.setCreatedAt(LocalDateTime.now());
        }
        return notification;
    }
}


