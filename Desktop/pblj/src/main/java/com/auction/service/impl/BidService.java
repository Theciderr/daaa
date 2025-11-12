package com.auction.service.impl;

import com.auction.dao.BidDAO;
import com.auction.dao.ProductDAO;
import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BidService {

    private final BidDAO bidDAO;
    private final ProductDAO productDAO;
    private final NotificationService notificationService;
    private final UserService userService;

    public BidService(BidDAO bidDAO, ProductDAO productDAO,
                      NotificationService notificationService,
                      UserService userService) {
        this.bidDAO = bidDAO;
        this.productDAO = productDAO;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    public Bid placeBid(int productId, int buyerId, BigDecimal amount) throws SQLException {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(product.getStartTime()) || now.isAfter(product.getEndTime())) {
            throw new IllegalStateException("Auction is not active");
        }
        if (!ProductService.STATUS_ACTIVE.equalsIgnoreCase(product.getStatus())
                && !ProductService.STATUS_SCHEDULED.equalsIgnoreCase(product.getStatus())) {
            throw new IllegalStateException("Auction is not open for bidding");
        }

        Optional<Bid> highestOpt = bidDAO.findHighestBid(productId);
        BigDecimal minimumBid = calculateMinimumBid(product, highestOpt.orElse(null));
        if (amount.compareTo(minimumBid) < 0) {
            throw new IllegalArgumentException("Bid must be at least " + minimumBid);
        }

        Bid bid = new Bid();
        bid.setProductId(productId);
        bid.setBuyerId(buyerId);
        bid.setBidAmount(amount);
        bid.setBidTime(now);
        int id = bidDAO.create(bid);
        bid.setId(id);

        productDAO.updateCurrentBid(productId, amount);
        if (!ProductService.STATUS_ACTIVE.equalsIgnoreCase(product.getStatus())) {
            productDAO.updateStatus(productId, ProductService.STATUS_ACTIVE);
        }

        notifyOutbidUser(product, highestOpt.orElse(null), buyerId, amount);
        return bid;
    }

    public Optional<Bid> findHighestBid(int productId) throws SQLException {
        return bidDAO.findHighestBid(productId);
    }

    public List<Bid> findBidsByProduct(int productId) throws SQLException {
        return bidDAO.findByProduct(productId);
    }

    public List<Bid> findBidsByBuyer(int buyerId) throws SQLException {
        return bidDAO.findByBuyer(buyerId);
    }

    public Optional<Bid> findWinningBid(int productId) throws SQLException {
        return bidDAO.findWinningBid(productId);
    }

    private BigDecimal calculateMinimumBid(Product product, Bid highestBid) {
        if (highestBid == null) {
            return product.getBasePrice();
        }
        return highestBid.getBidAmount().add(product.getBidIncrement());
    }

    private void notifyOutbidUser(Product product, Bid previousHighest, int newBidderId, BigDecimal amount) throws SQLException {
        if (previousHighest == null || previousHighest.getBuyerId() == newBidderId) {
            return;
        }
        Optional<User> outbidUserOpt = userService.findById(previousHighest.getBuyerId());
        Optional<User> newBidderOpt = userService.findById(newBidderId);
        if (outbidUserOpt.isPresent() && newBidderOpt.isPresent()) {
            notificationService.notifyOutbid(outbidUserOpt.get().getId(),
                    product.getId(),
                    product.getTitle(),
                    newBidderOpt.get().getName(),
                    amount.doubleValue());
        }
    }
}


