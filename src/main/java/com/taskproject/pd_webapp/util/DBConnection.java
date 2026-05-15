package com.taskproject.pd_webapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBConnection {
    // MySQL Configuration - Uses Environment Variables
    // Set environment variables: DB_URL, DB_USER, DB_PASSWORD
    // Falls back to defaults if not set
    
    private static final String DB_URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:mysql://localhost:3306/cybernova_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
    );
    
    private static final String DB_USER = System.getenv().getOrDefault(
            "DB_USER",
            "root"
    );
    
    private static final String DB_PASSWORD = System.getenv().getOrDefault(
            "DB_PASSWORD",
            "PASSWORD@123"
    );
    
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
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static synchronized void ensureInitialized() {
        if (initialized) {
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            // Verify database connection and tables exist
            statement.executeQuery("SELECT 1 FROM security_requests LIMIT 1");
            initialized = true;
        } catch (SQLException e) {
            throw new IllegalStateException(
                "Unable to connect to MySQL database. " +
                "Please ensure:\n" +
                "1. MySQL is running\n" +
                "2. Database exists\n" +
                "3. User can access the database\n" +
                "4. Environment variables are set correctly:\n" +
                "   - DB_URL: " + getDbUrlSafely() + "\n" +
                "   - DB_USER: " + DB_USER + "\n" +
                "5. Or run the setup script: scripts/mysql-setup.sql\n" +
                "Error: " + e.getMessage(), e);
        }
    }

    private static String getDbUrlSafely() {
        return DB_URL != null ? DB_URL.replaceAll("([?&][^&]*password[^&]*)", "[HIDDEN]") : "[NOT SET]";
    }
}
