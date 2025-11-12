package com.auction.service.impl;

import com.auction.dao.BidDAO;
import com.auction.dao.PaymentDAO;
import com.auction.dao.ProductDAO;
import com.auction.model.Bid;
import com.auction.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminService {

    private final ProductDAO productDAO;
    private final BidDAO bidDAO;
    private final PaymentDAO paymentDAO;
    private final NotificationService notificationService;

    public AdminService(ProductDAO productDAO, BidDAO bidDAO, PaymentDAO paymentDAO,
                        NotificationService notificationService) {
        this.productDAO = productDAO;
        this.bidDAO = bidDAO;
        this.paymentDAO = paymentDAO;
        this.notificationService = notificationService;
    }

    public List<Product> getAllAuctions() throws SQLException {
        return productDAO.findAll();
    }

    public List<Bid> getBidsForProduct(int productId) throws SQLException {
        return bidDAO.findByProduct(productId);
    }

    public void cancelBid(int bidId, int productId) throws SQLException {
        List<Bid> bids = bidDAO.findByProduct(productId);
        Optional<Bid> bidOpt = bids.stream()
                .filter(b -> b.getId() == bidId)
                .findFirst();
        bidDAO.delete(bidId);
        Optional<Bid> newHighest = bidDAO.findHighestBid(productId);
        productDAO.updateCurrentBid(productId, newHighest.map(Bid::getBidAmount).orElse(null));

        if (bidOpt.isPresent()) {
            Bid bid = bidOpt.get();
            notificationService.createNotification(bid.getBuyerId(), productId,
                    "Your bid was cancelled by admin.", "SYSTEM");
        }
    }

    public void cancelAuction(int productId) throws SQLException {
        productDAO.updateStatus(productId, ProductService.STATUS_CANCELLED);
        List<Bid> bids = bidDAO.findByProduct(productId);
        for (Bid bid : bids) {
            notificationService.createNotification(bid.getBuyerId(), productId,
                    "Auction cancelled by admin.", "SYSTEM");
        }
    }

    public BigDecimal calculateRevenue() throws SQLException {
        return paymentDAO.calculateTotalRevenue();
    }
}


