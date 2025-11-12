-- Initialization script for Docker MySQL container
-- This is the same schema and sample data used by the project (db/schema.sql)

DROP DATABASE IF EXISTS auction_db;
CREATE DATABASE auction_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE auction_db;

-- Users table stores buyers, sellers, admins
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash CHAR(64) NOT NULL,
    role ENUM('buyer','seller','admin') NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table represents auctions
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    base_price DECIMAL(12,2) NOT NULL CHECK (base_price > 0),
    current_bid DECIMAL(12,2) DEFAULT NULL,
    bid_increment DECIMAL(12,2) NOT NULL CHECK (bid_increment > 0),
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('scheduled','active','closed','cancelled') DEFAULT 'scheduled',
    image_url VARCHAR(255),
    CONSTRAINT fk_products_seller FOREIGN KEY (seller_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT chk_time_range CHECK (end_time > start_time)
);

CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_products_end_time ON products(end_time);

-- Bids table captures all bid activity
CREATE TABLE bids (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    buyer_id INT NOT NULL,
    bid_amount DECIMAL(12,2) NOT NULL CHECK (bid_amount > 0),
    bid_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bids_product FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_bids_buyer FOREIGN KEY (buyer_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_bids_product_time ON bids(product_id, bid_time DESC);

-- Notifications table provides XML-poll alerts
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT,
    message TEXT NOT NULL,
    type ENUM('OUTBID','AUCTION_CLOSE','PAYMENT','SYSTEM') NOT NULL,
    is_read TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_notifications_product FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE INDEX idx_notifications_user ON notifications(user_id, is_read);

-- Payments table stores escrow transactions
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bid_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL CHECK (amount > 0),
    status ENUM('PENDING','HELD','RELEASED','REFUNDED') NOT NULL DEFAULT 'PENDING',
    transaction_id VARCHAR(100) UNIQUE,
    paid_at DATETIME,
    CONSTRAINT fk_payments_bid FOREIGN KEY (bid_id) REFERENCES bids(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_payments_status ON payments(status);

-- Sample data ---------------------------------------------------------------

INSERT INTO users (name, email, password_hash, role, phone, address) VALUES
('Alice Buyer', 'alice@example.com', '9c1f1edb0b3f3d1c2f1479a9d37df44b0e9f4bf92d7e6d0b5d8e5e86b1dcbabd', 'buyer', '1234567890', '123 Market Street'),
('Bob Seller', 'bob@example.com', 'f1d2d2f924e986ac86fdf7b36c94bcdf32beec15b43f864b0db1fb9024d1f6a5', 'seller', '0987654321', '456 Commerce Ave'),
('Carol Admin', 'carol@example.com', '6ae8a75555209b0f898c0104d9bde8261ca2db9253b6d2f9b391d4bdf6f9d9d0', 'admin', '5550001111', '789 Admin Blvd');

-- Products (auctions)
INSERT INTO products (seller_id, title, description, base_price, current_bid, bid_increment, start_time, end_time, status, image_url) VALUES
(2, 'Vintage Camera', 'Category:Electronics|Condition:Used|Includes leather case', 100.00, 120.00, 10.00, NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 1 DAY, 'active', '/uploads/camera.jpg'),
(2, 'Antique Vase', 'Category:Home Decor|Condition:Antique|From 1920s collection', 200.00, NULL, 20.00, NOW() + INTERVAL 2 HOUR, NOW() + INTERVAL 3 DAY, 'scheduled', '/uploads/vase.jpg'),
(2, 'Gaming Laptop', 'Category:Computers|Condition:New|Includes warranty', 800.00, 850.00, 25.00, NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 1 HOUR, 'closed', '/uploads/laptop.jpg');

-- Bids
INSERT INTO bids (product_id, buyer_id, bid_amount, bid_time) VALUES
(1, 1, 110.00, NOW() - INTERVAL 20 MINUTE),
(1, 1, 120.00, NOW() - INTERVAL 10 MINUTE),
(3, 1, 825.00, NOW() - INTERVAL 30 HOUR),
(3, 1, 850.00, NOW() - INTERVAL 25 HOUR);

-- Notifications
INSERT INTO notifications (user_id, product_id, message, type, is_read) VALUES
(1, 1, 'You have been outbid on Vintage Camera by another user.', 'OUTBID', 0),
(1, 3, 'Auction closed: Gaming Laptop won at $850.00.', 'AUCTION_CLOSE', 1),
(2, 3, 'Auction closed: Gaming Laptop sold for $850.00.', 'AUCTION_CLOSE', 0);

-- Payments
INSERT INTO payments (bid_id, amount, status, transaction_id, paid_at) VALUES
(4, 850.00, 'RELEASED', 'TXN-20241111001', NOW() - INTERVAL 1 DAY);
