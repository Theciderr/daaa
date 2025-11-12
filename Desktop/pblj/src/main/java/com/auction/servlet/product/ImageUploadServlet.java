package com.auction.servlet.product;

import com.auction.service.impl.ProductService;
import com.auction.model.Product;
import com.auction.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MultipartConfig
public class ImageUploadServlet extends BaseServlet {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");
        Part filePart = req.getPart("image");
        if (filePart == null || filePart.getSize() == 0 || productIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image and productId are required");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productId");
            return;
        }

        String uploadsDir = getServletContext().getInitParameter("uploadPath");
        if (uploadsDir == null) {
            uploadsDir = "/uploads";
        }
        String realPath = getServletContext().getRealPath(uploadsDir);
        if (realPath == null) {
            realPath = getServletContext().getRealPath("/") + File.separator + "uploads";
        }
        Path uploadDirectory = Paths.get(realPath);
        Files.createDirectories(uploadDirectory);

        String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";
        int dotIndex = submittedFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = submittedFileName.substring(dotIndex);
        }
        String storedFileName = "product_" + productId + "_" + FORMATTER.format(LocalDateTime.now()) + extension;
        Path destination = uploadDirectory.resolve(storedFileName);

        try (InputStream input = filePart.getInputStream();
             FileOutputStream output = new FileOutputStream(destination.toFile())) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        }

        String imageUrl = uploadsDir + "/" + storedFileName;

        ProductService productService = services().getProductService();
        try {
            Product product = productService.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            product.setImageUrl(imageUrl);
            productService.updateProduct(product);
        } catch (SQLException | IllegalArgumentException e) {
            throw new ServletException("Unable to update product image", e);
        }

        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("<response><status>success</status><imageUrl>" + imageUrl + "</imageUrl></response>");
    }
}


