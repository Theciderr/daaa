package com.auction.servlet;

import com.auction.service.ServiceRegistry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {

    private ServiceRegistry serviceRegistry;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        Object registry = context.getAttribute("serviceRegistry");
        if (registry instanceof ServiceRegistry) {
            this.serviceRegistry = (ServiceRegistry) registry;
        } else {
            throw new ServletException("ServiceRegistry not initialized");
        }
    }

    protected ServiceRegistry services() {
        return serviceRegistry;
    }
}


