package com.auction.service.scheduler;

import com.auction.model.Bid;
import com.auction.model.Payment;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.ServiceRegistry;
import com.auction.service.impl.BidService;
import com.auction.service.impl.NotificationService;
import com.auction.service.impl.PaymentService;
import com.auction.service.impl.ProductService;
import com.auction.service.impl.UserService;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuctionScheduler {

    private static final Logger LOGGER = Logger.getLogger(AuctionScheduler.class.getName());
    private ScheduledExecutorService executorService;

    public void start(ServletContext context, ServiceRegistry serviceRegistry) {
        executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "AuctionScheduler");
            t.setDaemon(true);
            return t;
        });
        executorService.scheduleAtFixedRate(() -> processAuctions(serviceRegistry), 5, 30, TimeUnit.SECONDS);
        context.setAttribute("auctionScheduler", this);
    }

    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    private void processAuctions(ServiceRegistry registry) {
        ProductService productService = registry.getProductService();
        BidService bidService = registry.getBidService();
        NotificationService notificationService = registry.getNotificationService();
        UserService userService = registry.getUserService();

        try {
            List<Product> expired = productService.findExpiredAuctions(LocalDateTime.now());
            for (Product product : expired) {
                Optional<Bid> winningBidOpt = bidService.findWinningBid(product.getId());
                productService.updateStatus(product.getId(), ProductService.STATUS_CLOSED);

                if (winningBidOpt.isPresent()) {
                    Bid winningBid = winningBidOpt.get();
                    productService.updateCurrentBid(product.getId(), winningBid.getBidAmount());

                    PaymentService paymentSvc = registry.getPaymentService();
                    try {
                        Payment payment = paymentSvc.initiatePayment(winningBid.getId(), winningBid.getBidAmount());
                        notificationService.notifyPayment(winningBid.getBuyerId(), product.getId(),
                                payment.getTransactionId(), payment.getStatus());
                    } catch (SQLException ex) {
                        LOGGER.log(Level.WARNING, "Unable to initiate payment for product " + product.getId(), ex);
                    }

                    Optional<User> sellerOpt = userService.findById(product.getSellerId());
                    Optional<User> winnerOpt = userService.findById(winningBid.getBuyerId());
                    String productTitle = product.getTitle();

                    if (sellerOpt.isPresent()) {
                        notificationService.notifyAuctionClose(sellerOpt.get().getId(), product.getId(),
                                productTitle, "Sold at $" + winningBid.getBidAmount());
                    }

                    if (winnerOpt.isPresent()) {
                        notificationService.notifyAuctionClose(winnerOpt.get().getId(), product.getId(),
                                productTitle, "You won at $" + winningBid.getBidAmount());
                    }
                } else {
                    Optional<User> sellerOpt = userService.findById(product.getSellerId());
                    if (sellerOpt.isPresent()) {
                        notificationService.notifyAuctionClose(sellerOpt.get().getId(), product.getId(),
                                product.getTitle(), "No winning bids");
                    }
                    productService.updateCurrentBid(product.getId(), (BigDecimal) null);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error processing auctions", e);
        }
    }
}


