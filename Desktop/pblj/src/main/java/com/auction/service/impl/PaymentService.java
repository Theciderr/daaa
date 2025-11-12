package com.auction.service.impl;

import com.auction.dao.PaymentDAO;
import com.auction.model.Payment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentService(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public Payment initiatePayment(int bidId, BigDecimal amount) throws SQLException {
        Payment payment = new Payment();
        payment.setBidId(bidId);
        payment.setAmount(amount);
        payment.setStatus("HELD");
        payment.setTransactionId(generateTransactionId());
        payment.setPaidAt(LocalDateTime.now());
        int id = paymentDAO.create(payment);
        payment.setId(id);
        return payment;
    }

    public void releasePayment(int paymentId) throws SQLException {
        paymentDAO.updateStatus(paymentId, "RELEASED", LocalDateTime.now());
    }

    public void refundPayment(int paymentId) throws SQLException {
        paymentDAO.updateStatus(paymentId, "REFUNDED", LocalDateTime.now());
    }

    public Optional<Payment> findByBidId(int bidId) throws SQLException {
        return paymentDAO.findByBidId(bidId);
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }
}


