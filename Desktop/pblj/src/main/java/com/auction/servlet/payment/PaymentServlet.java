package com.auction.servlet.payment;

import com.auction.model.Bid;
import com.auction.model.Payment;
import com.auction.model.User;
import com.auction.service.impl.NotificationService;
import com.auction.service.impl.PaymentService;
import com.auction.servlet.BaseServlet;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class PaymentServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bidIdParam = req.getParameter("bidId");
        if (bidIdParam != null) {
            try {
                int bidId = Integer.parseInt(bidIdParam);
                services().getPaymentService().findByBidId(bidId).ifPresent(payment -> req.setAttribute("payment", payment));
            } catch (NumberFormatException | SQLException e) {
                throw new ServletException("Unable to load payment details", e);
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/payment/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("paymentResponse");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            xml.element("status", "error").element("message", "Authentication required");
            resp.getWriter().write(xml.build());
            return;
        }
        User user = (User) session.getAttribute("user");

        String action = req.getParameter("action");
        int bidId;
        try {
            bidId = Integer.parseInt(req.getParameter("bidId"));
        } catch (NumberFormatException e) {
            xml.element("status", "error").element("message", "Invalid bidId");
            resp.getWriter().write(xml.build());
            return;
        }

        PaymentService paymentService = services().getPaymentService();
        NotificationService notificationService = services().getNotificationService();

        try {
            Optional<Bid> bidOpt = services().getBidDAO().findById(bidId);
            if (!bidOpt.isPresent()) {
                xml.element("status", "error").element("message", "Bid not found");
                resp.getWriter().write(xml.build());
                return;
            }
            int productId = bidOpt.get().getProductId();
            Optional<Payment> paymentOpt = paymentService.findByBidId(bidId);
            if (!paymentOpt.isPresent()) {
                xml.element("status", "error").element("message", "Payment not found");
                resp.getWriter().write(xml.build());
                return;
            }
            Payment payment = paymentOpt.get();
            if ("release".equalsIgnoreCase(action)) {
                paymentService.releasePayment(payment.getId());
                notificationService.notifyPayment(user.getId(), productId, payment.getTransactionId(), "RELEASED");
                xml.element("status", "success").element("message", "Payment released");
            } else if ("refund".equalsIgnoreCase(action)) {
                paymentService.refundPayment(payment.getId());
                notificationService.notifyPayment(user.getId(), productId, payment.getTransactionId(), "REFUNDED");
                xml.element("status", "success").element("message", "Payment refunded");
            } else {
                xml.element("status", "error").element("message", "Unknown action");
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to update payment", e);
        }

        resp.getWriter().write(xml.build());
    }
}


