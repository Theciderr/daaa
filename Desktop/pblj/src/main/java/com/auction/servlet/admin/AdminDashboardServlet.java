package com.auction.servlet.admin;

import com.auction.model.Product;
import com.auction.service.impl.AdminService;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboardServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService = services().getAdminService();
        try {
            List<Product> auctions = adminService.getAllAuctions();
            BigDecimal revenue = adminService.calculateRevenue();
            req.setAttribute("auctions", auctions);
            req.setAttribute("revenue", revenue);
        } catch (SQLException e) {
            throw new ServletException("Unable to load admin dashboard", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }
}


