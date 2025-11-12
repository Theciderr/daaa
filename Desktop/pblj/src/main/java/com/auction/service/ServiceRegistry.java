package com.auction.service;

import com.auction.dao.BidDAO;
import com.auction.dao.NotificationDAO;
import com.auction.dao.PaymentDAO;
import com.auction.dao.ProductDAO;
import com.auction.dao.UserDAO;
import com.auction.service.impl.AdminService;
import com.auction.service.impl.BidService;
import com.auction.service.impl.HistoryService;
import com.auction.service.impl.NotificationService;
import com.auction.service.impl.PaymentService;
import com.auction.service.impl.ProductService;
import com.auction.service.impl.SearchService;
import com.auction.service.impl.UserService;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.util.Objects;

public class ServiceRegistry {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final BidDAO bidDAO;
    private final NotificationDAO notificationDAO;
    private final PaymentDAO paymentDAO;

    private final UserService userService;
    private final ProductService productService;
    private final BidService bidService;
    private final NotificationService notificationService;
    private final PaymentService paymentService;
    private final SearchService searchService;
    private final HistoryService historyService;
    private final AdminService adminService;

    public ServiceRegistry(ServletContext context) {
        DataSource dataSource = (DataSource) context.getAttribute("dataSource");
        Objects.requireNonNull(dataSource, "DataSource missing from servlet context");

        this.userDAO = new UserDAO(dataSource);
        this.productDAO = new ProductDAO(dataSource);
        this.bidDAO = new BidDAO(dataSource);
        this.notificationDAO = new NotificationDAO(dataSource);
        this.paymentDAO = new PaymentDAO(dataSource);

        this.notificationService = new NotificationService(notificationDAO);
        this.userService = new UserService(userDAO);
        this.productService = new ProductService(productDAO);
        this.paymentService = new PaymentService(paymentDAO);
        this.bidService = new BidService(bidDAO, productDAO, notificationService, userService);
        this.searchService = new SearchService(productDAO);
        this.historyService = new HistoryService(bidDAO, productDAO, userService);
        this.adminService = new AdminService(productDAO, bidDAO, paymentDAO, notificationService);

        context.setAttribute("serviceRegistry", this);
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public BidService getBidService() {
        return bidService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public BidDAO getBidDAO() {
        return bidDAO;
    }

    public NotificationDAO getNotificationDAO() {
        return notificationDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }
}


