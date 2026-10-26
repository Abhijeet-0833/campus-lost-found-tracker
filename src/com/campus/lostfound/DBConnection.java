package com.campus.lostfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection provides database connectivity using JDBC.
 * Configure connection details through environment variables.
 */
public class DBConnection {

    private static final String URL = System.getenv().getOrDefault(
            "CAMPUS_DB_URL",
            "jdbc:mysql://localhost:3306/campus_lost_found?useSSL=true&serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("CAMPUS_DB_USER", "root");
    private static final String PASSWORD = System.getenv("CAMPUS_DB_PASSWORD");
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] MySQL JDBC Driver not found! Make sure mysql-connector-j.jar is added to the Build Path.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException("CAMPUS_DB_PASSWORD must be configured in the environment.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("[DATABASE ERROR] Unable to connect to MySQL database.");
            System.err.println("Check that MySQL is running, the database exists, and CAMPUS_DB_URL/CAMPUS_DB_USER/CAMPUS_DB_PASSWORD are configured.");
            return false;
        }
    }
}
