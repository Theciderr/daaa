package com.auction.servlet.bid;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.impl.BidService;
import com.auction.service.impl.ProductService;
import com.auction.service.impl.UserService;
import com.auction.servlet.BaseServlet;
import com.auction.util.DateTimeUtil;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AuctionFeedServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("auction");

        int productId;
        try {
            productId = Integer.parseInt(req.getParameter("productId"));
        } catch (NumberFormatException e) {
            xml.element("status", "error").element("message", "Invalid productId");
            resp.getWriter().write(xml.build());
            return;
        }

        ProductService productService = services().getProductService();
        BidService bidService = services().getBidService();
        UserService userService = services().getUserService();

        try {
            Optional<Product> productOpt = productService.findById(productId);
            if (!productOpt.isPresent()) {
                xml.element("status", "error").element("message", "Product not found");
                resp.getWriter().write(xml.build());
                return;
            }
            Product product = productOpt.get();
            xml.element("status", "success");
            xml.openElement("details")
                    .element("title", product.getTitle())
                    .element("currentBid", product.getCurrentBid() != null ? product.getCurrentBid() : product.getBasePrice())
                    .element("startTime", product.getStartTime().toString())
                    .element("endTime", product.getEndTime().toString())
                    .element("timeLeftSeconds", Math.max(0, DateTimeUtil.secondsUntil(product.getEndTime())))
                    .element("status", product.getStatus())
                    .closeElement();

            Optional<Bid> highestBidOpt = bidService.findHighestBid(productId);
            if (highestBidOpt.isPresent()) {
                Bid bid = highestBidOpt.get();
                xml.openElement("highestBid")
                        .element("amount", bid.getBidAmount())
                        .element("time", bid.getBidTime().toString());
                Optional<User> bidderOpt = userService.findById(bid.getBuyerId());
                bidderOpt.ifPresent(user -> xml.element("bidder", user.getName()));
                xml.closeElement();
            }

            xml.openElement("recentBids");
            List<Bid> bidList = bidService.findBidsByProduct(productId);
            int limit = Math.min(bidList.size(), 10);
            for (int i = 0; i < limit; i++) {
                Bid bid = bidList.get(i);
                xml.openElement("bid")
                        .element("amount", bid.getBidAmount())
                        .element("time", bid.getBidTime().toString());
                userService.findById(bid.getBuyerId()).ifPresent(user -> xml.element("bidder", user.getName()));
                xml.closeElement();
            }
            xml.closeElement();

        } catch (SQLException e) {
            throw new ServletException("Unable to load auction feed", e);
        }

        resp.getWriter().write(xml.build());
    }
}


