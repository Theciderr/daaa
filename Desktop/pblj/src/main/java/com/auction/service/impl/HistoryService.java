package com.auction.service.impl;

import com.auction.dao.BidDAO;
import com.auction.dao.ProductDAO;
import com.auction.model.Bid;
import com.auction.model.BuyerHistoryRecord;
import com.auction.model.Product;
import com.auction.model.SellerHistoryRecord;
import com.auction.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HistoryService {

    private final BidDAO bidDAO;
    private final ProductDAO productDAO;
    private final UserService userService;

    public HistoryService(BidDAO bidDAO, ProductDAO productDAO, UserService userService) {
        this.bidDAO = bidDAO;
        this.productDAO = productDAO;
        this.userService = userService;
    }

    public List<BuyerHistoryRecord> getBuyerHistory(int buyerId) throws SQLException {
        List<Bid> bids = bidDAO.findByBuyer(buyerId);
        Map<Integer, Bid> winningBidCache = new HashMap<>();
        Map<Integer, Product> productCache = new HashMap<>();
        List<BuyerHistoryRecord> history = new ArrayList<>();

        for (Bid bid : bids) {
            Product product = productCache.computeIfAbsent(bid.getProductId(), pid -> fetchProduct(pid));
            if (product == null) {
                continue;
            }
            Bid winningBid = winningBidCache.computeIfAbsent(product.getId(), pid -> fetchWinningBid(pid));
            boolean won = winningBid != null && winningBid.getBuyerId() == buyerId
                    && winningBid.getBidAmount().compareTo(bid.getBidAmount()) == 0;

            BuyerHistoryRecord record = new BuyerHistoryRecord();
            record.setProductId(product.getId());
            record.setProductTitle(product.getTitle());
            record.setBidAmount(bid.getBidAmount());
            record.setBidTime(bid.getBidTime());
            record.setAuctionEndTime(product.getEndTime());
            record.setWon(won);
            history.add(record);
        }
        return history;
    }

    public List<SellerHistoryRecord> getSellerHistory(int sellerId) throws SQLException {
        List<Product> products = productDAO.findBySeller(sellerId);
        List<SellerHistoryRecord> history = new ArrayList<>();
        for (Product product : products) {
            if (!ProductService.STATUS_CLOSED.equalsIgnoreCase(product.getStatus())
                    && !ProductService.STATUS_CANCELLED.equalsIgnoreCase(product.getStatus())) {
                continue;
            }
            SellerHistoryRecord record = new SellerHistoryRecord();
            record.setProductId(product.getId());
            record.setProductTitle(product.getTitle());
            record.setEndTime(product.getEndTime());
            record.setStatus(product.getStatus());

            Optional<Bid> winningBidOpt = bidDAO.findWinningBid(product.getId());
            if (winningBidOpt.isPresent()) {
                Bid winningBid = winningBidOpt.get();
                record.setFinalPrice(winningBid.getBidAmount());
                Optional<User> buyerOpt = userService.findById(winningBid.getBuyerId());
                buyerOpt.ifPresent(user -> record.setWinnerName(user.getName()));
            } else {
                BigDecimal fallback = product.getCurrentBid() != null ? product.getCurrentBid() : product.getBasePrice();
                record.setFinalPrice(fallback);
            }
            history.add(record);
        }
        return history;
    }

    private Product fetchProduct(int productId) {
        try {
            Optional<Product> productOpt = productDAO.findById(productId);
            return productOpt.orElse(null);
        } catch (SQLException e) {
            return null;
        }
    }

    private Bid fetchWinningBid(int productId) {
        try {
            Optional<Bid> winningOpt = bidDAO.findWinningBid(productId);
            return winningOpt.orElse(null);
        } catch (SQLException e) {
            return null;
        }
    }
}


