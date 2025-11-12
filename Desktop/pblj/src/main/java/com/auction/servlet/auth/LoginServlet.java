package com.auction.servlet.auth;

import com.auction.model.User;
import com.auction.service.impl.UserService;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = services().getUserService();
        try {
            Optional<User> userOpt = userService.authenticate(email, password);
            if (userOpt.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("user", userOpt.get());
                session.removeAttribute("flash");
                redirectByRole(resp, req, userOpt.get().getRole());
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to authenticate user", e);
        }
    }

    private void redirectByRole(HttpServletResponse resp, HttpServletRequest req, String role) throws IOException {
        String contextPath = req.getContextPath();
        if ("admin".equals(role)) {
            resp.sendRedirect(contextPath + "/admin/dashboard");
        } else if ("seller".equals(role)) {
            resp.sendRedirect(contextPath + "/seller/products");
        } else {
            resp.sendRedirect(contextPath + "/search");
        }
    }
}


