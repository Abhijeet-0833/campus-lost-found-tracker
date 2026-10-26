-- ==============================================================
-- CAMPUS LOST & FOUND TRACKER - DATABASE CREATION SCRIPT
-- ==============================================================
-- Target Database: MySQL 8.0+
-- Project: Campus Lost & Found Tracker (JDBC)
-- ==============================================================
-- WARNING: The reset section below drops the existing items table.
-- Back up any data before running this script against a database
-- that contains information you need to keep.

CREATE DATABASE IF NOT EXISTS campus_lost_found;
USE campus_lost_found;

DROP TABLE IF EXISTS items;

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    description VARCHAR(255),
    location VARCHAR(100),
    report_type VARCHAR(10) NOT NULL CHECK (report_type IN ('LOST', 'FOUND')),
    reported_by VARCHAR(100),
    contact_number VARCHAR(20),
    reported_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'LOST' CHECK (status IN ('LOST', 'FOUND', 'RETURNED'))
);

-- Fictional demonstration data only. Do not insert real contact details.
INSERT INTO items (item_name, category, description, location, report_type, reported_by, contact_number, reported_date, status)
VALUES
('Sample ID Card', 'Documents', 'Fictional sample ID card; no real identifier', 'Sample Library Area', 'LOST', 'Sample User A', '0000000000', '2026-10-15', 'LOST'),
('Sample Earbuds', 'Electronics', 'Fictional sample item', 'Sample Computer Lab', 'FOUND', 'Sample User B', '0000000000', '2026-10-16', 'FOUND'),
('Sample Calculator', 'Stationery', 'Fictional sample item', 'Sample Auditorium', 'LOST', 'Sample User C', '0000000000', '2026-10-17', 'LOST'),
('Sample Bottle', 'Personal Belongings', 'Fictional sample item', 'Sample Sports Area', 'FOUND', 'Sample User D', '0000000000', '2026-10-18', 'FOUND'),
('Sample Charger', 'Electronics', 'Fictional sample item', 'Sample Cafeteria', 'LOST', 'Sample User E', '0000000000', '2026-10-14', 'RETURNED');

SELECT * FROM items;
