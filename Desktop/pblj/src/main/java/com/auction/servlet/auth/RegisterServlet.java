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

public class RegisterServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPhone(phone);
        user.setAddress(address);

        UserService userService = services().getUserService();
        try {
            userService.register(user, password);
            HttpSession session = req.getSession();
            session.setAttribute("flash", "Registration successful. Please login.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to register user", e);
        }
    }
}


