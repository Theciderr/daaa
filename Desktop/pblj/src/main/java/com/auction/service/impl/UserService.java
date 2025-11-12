package com.auction.service.impl;

import com.auction.dao.UserDAO;
import com.auction.model.User;
import com.auction.util.PasswordUtil;
import com.auction.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> authenticate(String email, String password) throws SQLException {
        Optional<User> userOpt = userDAO.findByEmail(email);
        if (!userOpt.isPresent()) {
            return Optional.empty();
        }
        User user = userOpt.get();
        if (PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User register(User user, String rawPassword) throws SQLException {
        validateUser(user, rawPassword);
        Optional<User> existing = userDAO.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        user.setPasswordHash(PasswordUtil.hashPassword(rawPassword));
        int userId = userDAO.create(user);
        user.setId(userId);
        return user;
    }

    public void updateProfile(User user) throws SQLException {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (!ValidationUtil.isValidPhone(user.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        userDAO.updateProfile(user);
    }

    public Optional<User> findById(int id) throws SQLException {
        return userDAO.findById(id);
    }

    public List<User> findByRole(String role) throws SQLException {
        return userDAO.findAllByRole(role);
    }

    private void validateUser(User user, String rawPassword) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (!ValidationUtil.isValidPhone(user.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}


