package com.auction.config;

import com.auction.service.ServiceRegistry;
import com.auction.service.scheduler.AuctionScheduler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());
    private AuctionScheduler auctionScheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String dataSourceName = context.getInitParameter("jdbc/DataSourceName");

        try {
            InitialContext ic = new InitialContext();
            DataSource dataSource = (DataSource) ic.lookup(dataSourceName);
            context.setAttribute("dataSource", dataSource);
            LOGGER.info("DataSource bound to servlet context");
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Unable to lookup DataSource: " + dataSourceName, e);
            throw new IllegalStateException("DataSource not configured", e);
        }

        ServiceRegistry serviceRegistry = new ServiceRegistry(context);
        context.setAttribute("serviceRegistry", serviceRegistry);

        auctionScheduler = new AuctionScheduler();
        auctionScheduler.start(context, serviceRegistry);
        LOGGER.info("AuctionScheduler started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (Objects.nonNull(auctionScheduler)) {
            auctionScheduler.stop();
        }
    }
}


