package com.auction.servlet.bid;

import com.auction.model.Bid;
import com.auction.model.User;
import com.auction.service.impl.BidService;
import com.auction.servlet.BaseServlet;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BidServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("bidResponse");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            xml.element("status", "error").element("message", "Authentication required");
            resp.getWriter().write(xml.build());
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!"buyer".equals(user.getRole())) {
            xml.element("status", "error").element("message", "Only buyers can place bids");
            resp.getWriter().write(xml.build());
            return;
        }

        int productId;
        BigDecimal amount;
        try {
            productId = Integer.parseInt(req.getParameter("productId"));
            amount = new BigDecimal(req.getParameter("bidAmount"));
        } catch (NumberFormatException e) {
            xml.element("status", "error").element("message", "Invalid bid input");
            resp.getWriter().write(xml.build());
            return;
        }

        BidService bidService = services().getBidService();
        try {
            Bid bid = bidService.placeBid(productId, user.getId(), amount);
            xml.element("status", "success");
            xml.openElement("bid")
                    .element("id", bid.getId())
                    .element("amount", bid.getBidAmount())
                    .element("time", bid.getBidTime().toString())
                    .closeElement();
        } catch (IllegalArgumentException | IllegalStateException e) {
            xml.element("status", "error").element("message", e.getMessage());
        } catch (SQLException e) {
            throw new ServletException("Unable to place bid", e);
        }

        resp.getWriter().write(xml.build());
    }
}


