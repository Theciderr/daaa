package com.auction.filter;

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
import java.util.HashSet;
import java.util.Set;

/**
 * Ensures the user is authenticated for protected endpoints.
 */
public class AuthenticationFilter implements Filter {

    private final Set<String> publicPaths = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        publicPaths.add("/login");
        publicPaths.add("/register");
        publicPaths.add("/logout");
        publicPaths.add("/resources/");
        publicPaths.add("/auction/feed");
        publicPaths.add("/search");
        publicPaths.add("/");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (isPublicPath(path) || isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        publicPaths.clear();
    }

    private boolean isPublicPath(String path) {
        if (path == null || path.isEmpty()) {
            return true;
        }
        for (String publicPath : publicPaths) {
            if (path.equals(publicPath) || path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");
    }
}


