package com.auction.servlet.notification;

import com.auction.model.Notification;
import com.auction.model.User;
import com.auction.service.impl.NotificationService;
import com.auction.servlet.BaseServlet;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("notifications");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            xml.element("status", "error").element("message", "Authentication required");
            resp.getWriter().write(xml.build());
            return;
        }
        User user = (User) session.getAttribute("user");

        NotificationService notificationService = services().getNotificationService();
        try {
            List<Notification> notifications = notificationService.findUnreadByUser(user.getId());
            xml.element("status", "success");
            xml.openElement("items");
            for (Notification notification : notifications) {
                xml.openElement("notification")
                        .element("id", notification.getId())
                        .element("productId", notification.getProductId() != null ? notification.getProductId() : -1)
                        .element("message", notification.getMessage())
                        .element("type", notification.getType())
                        .element("createdAt", notification.getCreatedAt().toString())
                        .closeElement();
            }
            xml.closeElement();
        } catch (SQLException e) {
            throw new ServletException("Unable to fetch notifications", e);
        }
        resp.getWriter().write(xml.build());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String[] ids = req.getParameterValues("notificationId");
        if (ids == null || ids.length == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "notificationId required");
            return;
        }
        List<Integer> notificationIds = new ArrayList<>();
        for (String id : ids) {
            try {
                notificationIds.add(Integer.parseInt(id));
            } catch (NumberFormatException ignored) {
            }
        }

        if (notificationIds.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No valid notificationId");
            return;
        }

        try {
            services().getNotificationService().markAsRead(notificationIds);
        } catch (SQLException e) {
            throw new ServletException("Unable to update notifications", e);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}


