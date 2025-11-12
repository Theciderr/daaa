package com.auction.servlet.search;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.impl.SearchService;
import com.auction.servlet.BaseServlet;
import com.auction.util.Pagination;
import com.auction.util.XmlResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");
        if (productIdParam != null && !productIdParam.isEmpty()) {
            serveAuctionDetail(req, resp, productIdParam);
            return;
        }

        String category = req.getParameter("category");
        BigDecimal minPrice = parseBigDecimal(req.getParameter("minPrice"));
        BigDecimal maxPrice = parseBigDecimal(req.getParameter("maxPrice"));
        Integer sellerId = parseInteger(req.getParameter("sellerId"));
        String sort = req.getParameter("sort");

        int page = Pagination.sanitizePage(req.getParameter("page"));
        int size = Pagination.sanitizeSize(req.getParameter("size"), 10, 50);
        int offset = Pagination.offset(page, size);

        SearchService searchService = services().getSearchService();
        try {
            List<Product> products = searchService.search(category, minPrice, maxPrice, sellerId, sort, offset, size);
            if ("xml".equalsIgnoreCase(req.getParameter("format"))) {
                writeXmlResponse(resp, products);
            } else {
                req.setAttribute("products", products);
                req.setAttribute("page", page);
                req.setAttribute("size", size);
                req.getRequestDispatcher("/WEB-INF/views/search/results.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to perform search", e);
        }
    }

    private void serveAuctionDetail(HttpServletRequest req, HttpServletResponse resp, String productIdParam)
            throws ServletException, IOException {
        int productId;
        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product id");
            return;
        }

        try {
            Product product = services().getProductService().findById(productId)
                    .orElseThrow(() -> new ServletException("Product not found"));
            req.setAttribute("product", product);

            List<Bid> bids = services().getBidService().findBidsByProduct(productId);
            List<Map<String, Object>> bidRows = new ArrayList<>();
            String highestBidderName = "—";
            for (Bid bid : bids) {
                Map<String, Object> row = new HashMap<>();
                services().getUserService().findById(bid.getBuyerId())
                        .ifPresent(user -> row.put("bidderName", user.getName()));
                if (!row.containsKey("bidderName")) {
                    row.put("bidderName", "Anonymous");
                }
                row.put("amount", bid.getBidAmount());
                row.put("time", bid.getBidTime());
                bidRows.add(row);
            }
            if (!bids.isEmpty()) {
                Bid highest = bids.get(0);
                highestBidderName = services().getUserService().findById(highest.getBuyerId())
                        .map(User::getName)
                        .orElse("Anonymous");
                req.setAttribute("highestBid", highest);
            } else {
                req.setAttribute("highestBid", new Bid());
            }
            req.setAttribute("highestBidBidder", highestBidderName);
            req.setAttribute("bids", bidRows);
            req.getRequestDispatcher("/WEB-INF/views/auction/detail.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load auction detail", e);
        }
    }

    private void writeXmlResponse(HttpServletResponse resp, List<Product> products) throws IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        XmlResponseBuilder xml = new XmlResponseBuilder().startDocument("searchResults");
        xml.element("status", "success");
        xml.openElement("products");
        for (Product product : products) {
            xml.openElement("product")
                    .element("id", product.getId())
                    .element("title", product.getTitle())
                    .element("currentBid", product.getCurrentBid() != null ? product.getCurrentBid() : product.getBasePrice())
                    .element("endTime", product.getEndTime().toString())
                    .element("status", product.getStatus())
                    .element("imageUrl", product.getImageUrl())
                    .closeElement();
        }
        xml.closeElement();
        resp.getWriter().write(xml.build());
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}


