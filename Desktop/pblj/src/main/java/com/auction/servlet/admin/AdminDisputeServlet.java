package com.auction.servlet.admin;

import com.auction.service.impl.AdminService;
import com.auction.servlet.BaseServlet;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminDisputeServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("adminResponse");

        String action = req.getParameter("action");
        AdminService adminService = services().getAdminService();

        try {
            if ("cancelBid".equalsIgnoreCase(action)) {
                int bidId = Integer.parseInt(req.getParameter("bidId"));
                int productId = Integer.parseInt(req.getParameter("productId"));
                adminService.cancelBid(bidId, productId);
                xml.element("status", "success").element("message", "Bid cancelled");
            } else if ("cancelAuction".equalsIgnoreCase(action)) {
                int productId = Integer.parseInt(req.getParameter("productId"));
                adminService.cancelAuction(productId);
                xml.element("status", "success").element("message", "Auction cancelled");
            } else {
                xml.element("status", "error").element("message", "Unknown action");
            }
        } catch (NumberFormatException e) {
            xml.element("status", "error").element("message", "Invalid parameters");
        } catch (SQLException e) {
            throw new ServletException("Admin action failed", e);
        }

        resp.getWriter().write(xml.build());
    }
}


