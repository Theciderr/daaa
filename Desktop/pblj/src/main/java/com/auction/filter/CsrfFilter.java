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
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Basic synchronizer token CSRF protection filter.
 */
public class CsrfFilter implements Filter {

    public static final String CSRF_TOKEN_SESSION_KEY = "csrfToken";
    public static final String CSRF_TOKEN_PARAM = "_csrf";
    public static final String CSRF_TOKEN_HEADER = "X-CSRF-Token";

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public void init(FilterConfig filterConfig) {
        // no-op
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_SESSION_KEY);
        if (sessionToken == null) {
            sessionToken = generateToken();
            session.setAttribute(CSRF_TOKEN_SESSION_KEY, sessionToken);
        }

        if (requiresCsrfProtection(httpRequest)) {
            String requestToken = resolveToken(httpRequest);
            if (requestToken == null || !sessionToken.equals(requestToken)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // no-op
    }

    private boolean requiresCsrfProtection(HttpServletRequest request) {
        String method = request.getMethod();
        return "POST".equalsIgnoreCase(method)
                || "PUT".equalsIgnoreCase(method)
                || "DELETE".equalsIgnoreCase(method)
                || "PATCH".equalsIgnoreCase(method);
    }

    private String resolveToken(HttpServletRequest request) {
        String headerToken = request.getHeader(CSRF_TOKEN_HEADER);
        if (headerToken != null && !headerToken.isEmpty()) {
            return headerToken;
        }
        String paramToken = request.getParameter(CSRF_TOKEN_PARAM);
        if (paramToken != null && !paramToken.isEmpty()) {
            return paramToken;
        }
        Object attributeToken = request.getAttribute(CSRF_TOKEN_PARAM);
        if (attributeToken instanceof String) {
            return (String) attributeToken;
        }
        return null;
    }

    private String generateToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}


