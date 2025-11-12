package com.auction.service.impl;

import com.auction.dao.NotificationDAO;
import com.auction.model.Notification;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationService {

    private final NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public Notification createNotification(int userId, Integer productId, String message, String type) throws SQLException {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setProductId(productId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        int id = notificationDAO.create(notification);
        notification.setId(id);
        return notification;
    }

    public void notifyOutbid(int userId, int productId, String productTitle, String bidderName, double amount) throws SQLException {
        String message = "Outbid on " + productTitle + " by " + bidderName + " with $" + amount;
        createNotification(userId, productId, message, "OUTBID");
    }

    public void notifyAuctionClose(int userId, int productId, String productTitle, String result) throws SQLException {
        String message = "Auction closed for " + productTitle + ": " + result;
        createNotification(userId, productId, message, "AUCTION_CLOSE");
    }

    public void notifyPayment(int userId, int productId, String transactionId, String status) throws SQLException {
        String message = "Payment " + status + " for product " + productId + ", transaction " + transactionId;
        createNotification(userId, productId, message, "PAYMENT");
    }

    public List<Notification> findUnreadByUser(int userId) throws SQLException {
        return notificationDAO.findUnreadByUser(userId);
    }

    public void markAsRead(List<Integer> notificationIds) throws SQLException {
        notificationDAO.markAsRead(notificationIds);
    }
}


