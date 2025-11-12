package com.auction.filter;

import com.auction.model.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Enforces role-based access control for URL patterns.
 */
public class RoleFilter implements Filter {

    private final Map<String, Set<String>> roleMappings = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        roleMappings.put("/seller/", Collections.singleton("seller"));
        roleMappings.put("/buyer/", Collections.singleton("buyer"));
        roleMappings.put("/admin/", Collections.singleton("admin"));
        roleMappings.put("/bid", Collections.singleton("buyer"));
        roleMappings.put("/payment", Collections.singleton("buyer"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        Set<String> requiredRoles = resolveRoles(path);
        if (requiredRoles.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if ("admin".equals(user.getRole())) {
            chain.doFilter(request, response);
            return;
        }

        if (!requiredRoles.contains(user.getRole())) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        roleMappings.clear();
    }

    private Set<String> resolveRoles(String path) {
        if (path == null) {
            return Collections.emptySet();
        }
        return roleMappings.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(Collections.emptySet());
    }
}


