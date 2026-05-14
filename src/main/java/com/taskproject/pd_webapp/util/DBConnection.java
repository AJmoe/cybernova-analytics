package com.taskproject.pd_webapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBConnection {
    // MySQL Configuration
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DB_NAME = "cybernova_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "PASSWORD@123"; // CyberNova Analytics Database
    private static final String JDBC_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
        + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static volatile boolean initialized = false;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("MySQL JDBC Driver not available", e);
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        ensureInitialized();
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    public static synchronized void ensureInitialized() {
        if (initialized) {
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            // Verify database connection and tables exist
            statement.executeQuery("SELECT 1 FROM security_requests LIMIT 1");
            initialized = true;
        } catch (SQLException e) {
            throw new IllegalStateException(
                "Unable to connect to MySQL database. " +
                "Please ensure:\n" +
                "1. MySQL is running on " + DB_HOST + ":" + DB_PORT + "\n" +
                "2. Database '" + DB_NAME + "' exists\n" +
                "3. User '" + DB_USER + "' can access the database\n" +
                "4. Run the setup script: scripts/mysql-setup.sql\n" +
                "Error: " + e.getMessage(), e);
        }
    }
}

