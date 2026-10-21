-- ==============================================================
-- CAMPUS LOST & FOUND TRACKER - DATABASE CREATION SCRIPT
-- ==============================================================
-- Target Database: MySQL 8.0+
-- Project: Campus Lost & Found Tracker (JDBC)
-- ==============================================================

-- 1. Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS campus_lost_found;

-- 2. Switch to the newly created database
USE campus_lost_found;

-- 3. Drop the table if it already exists (useful for clean reset)
DROP TABLE IF EXISTS items;

-- 4. Create the 'items' table
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

-- ==============================================================
-- SAMPLE INITIAL DATA (Optional for quick demonstration & testing)
-- ==============================================================

INSERT INTO items (item_name, category, description, location, report_type, reported_by, contact_number, reported_date, status)
VALUES 
('Student ID Card', 'Documents', 'Blue lanyard ID card with roll no 2024CS101', 'Central Library Reading Hall', 'LOST', 'Aarav Sharma', '9876543210', '2026-10-15', 'LOST'),
('Wireless Earbuds', 'Electronics', 'Black boat airdopes in black charging case', 'Computer Lab 3, 2nd Floor', 'FOUND', 'Priya Patel', '9123456780', '2026-10-16', 'FOUND'),
('Scientific Calculator', 'Stationery', 'Casio fx-991EX ClassWiz calculator', 'Main Auditorium Row G', 'LOST', 'Rohan Verma', '9988776655', '2026-10-17', 'LOST'),
('Water Bottle', 'Personal Belongings', 'Steel Milton thermosteel flask 1 Litre', 'Sports Complex Badminton Court', 'FOUND', 'Sneha Kulkarni', '9811223344', '2026-10-18', 'FOUND'),
('Laptop Charger', 'Electronics', 'HP 65W Blue-pin laptop adapter', 'Cafeteria Table 12', 'LOST', 'Aditya Singh', '9700112233', '2026-10-14', 'RETURNED');

-- Verify inserted records
SELECT * FROM items;
