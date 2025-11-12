package com.auction.servlet.history;

import com.auction.model.BuyerHistoryRecord;
import com.auction.model.SellerHistoryRecord;
import com.auction.model.User;
import com.auction.service.impl.HistoryService;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HistoryServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        HistoryService historyService = services().getHistoryService();

        try {
            if ("buyer".equals(user.getRole())) {
                List<BuyerHistoryRecord> buyerHistory = historyService.getBuyerHistory(user.getId());
                req.setAttribute("history", buyerHistory);
                req.getRequestDispatcher("/WEB-INF/views/history/buyer.jsp").forward(req, resp);
            } else if ("seller".equals(user.getRole())) {
                List<SellerHistoryRecord> sellerHistory = historyService.getSellerHistory(user.getId());
                req.setAttribute("history", sellerHistory);
                req.getRequestDispatcher("/WEB-INF/views/history/seller.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to load history", e);
        }
    }
}


