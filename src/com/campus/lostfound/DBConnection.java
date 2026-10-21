package com.campus.lostfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection provides database connectivity using JDBC.
 * It manages MySQL credentials and returns an active Connection object.
 */
public class DBConnection {

    // Database configuration constants
    // Update USER and PASSWORD to match your local MySQL configuration
    private static final String URL = "jdbc:mysql://localhost:3306/campus_lost_found?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // XAMPP default is empty (""). Change if your MySQL has a password.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Static block to register the MySQL JDBC Driver
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] MySQL JDBC Driver not found! Make sure mysql-connector-j.jar is added to the Build Path.");
            e.printStackTrace();
        }
    }

    /**
     * Establishes and returns a new database Connection.
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Utility method to test if database connection is working properly.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("\n[DATABASE ERROR] Unable to connect to MySQL database.");
            System.err.println("Details: " + e.getMessage());
            System.err.println("\nTroubleshooting tips:");
            System.err.println(" 1. Ensure MySQL Server is running (e.g., via MySQL Workbench / XAMPP / Windows Services).");
            System.err.println(" 2. Verify that database 'campus_lost_found' exists (run sql/schema.sql).");
            System.err.println(" 3. Check USER ('" + USER + "') and PASSWORD in DBConnection.java.");
            return false;
        }
    }
}
