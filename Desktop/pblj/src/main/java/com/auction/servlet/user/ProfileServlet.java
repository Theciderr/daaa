package com.auction.servlet.user;

import com.auction.model.User;
import com.auction.service.impl.UserService;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        sessionUser.setName(req.getParameter("name"));
        sessionUser.setPhone(req.getParameter("phone"));
        sessionUser.setAddress(req.getParameter("address"));

        UserService userService = services().getUserService();
        try {
            userService.updateProfile(sessionUser);
            session.setAttribute("user", sessionUser);
            req.setAttribute("success", "Profile updated successfully");
            req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to update profile", e);
        }
    }
}


