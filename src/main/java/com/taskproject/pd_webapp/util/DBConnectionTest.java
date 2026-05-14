package com.taskproject.pd_webapp.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Database Connection Test
 * Tests the database connection to CyberNova Analytics database
 */
public class DBConnectionTest {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("CyberNova Analytics - Database Connection Test");
        System.out.println("========================================\n");
        
        try {
            System.out.println("[1] Attempting to connect to database...");
            Connection connection = DBConnection.getConnection();
            
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ CONNECTION SUCCESSFUL!\n");
                
                // Get database metadata
                DatabaseMetaData metadata = connection.getMetaData();
                
                System.out.println("[2] Database Information:");
                System.out.println("    - Driver Name: " + metadata.getDriverName());
                System.out.println("    - Driver Version: " + metadata.getDriverVersion());
                System.out.println("    - Database Product Name: " + metadata.getDatabaseProductName());
                System.out.println("    - Database Product Version: " + metadata.getDatabaseProductVersion());
                System.out.println("    - URL: " + metadata.getURL());
                System.out.println("    - Username: " + metadata.getUserName());
                
                System.out.println("\n[3] Database Tables:");
                var tables = metadata.getTables("cybernova_db", null, "%", new String[]{"TABLE"});
                int tableCount = 0;
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("    ✓ " + tableName);
                    tableCount++;
                }
                System.out.println("\n    Total tables found: " + tableCount);
                
                connection.close();
                
                System.out.println("\n========================================");
                System.out.println("✅ ALL TESTS PASSED!");
                System.out.println("========================================");
                System.exit(0);
            } else {
                throw new SQLException("Connection is null or closed");
            }
            
        } catch (SQLException e) {
            System.out.println("\n❌ CONNECTION FAILED!\n");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nPlease verify:");
            System.out.println("  1. MySQL is running");
            System.out.println("  2. Database 'cybernova_db' exists");
            System.out.println("  3. Username 'root' has access");
            System.out.println("  4. Password is 'PASSWORD@123'");
            System.out.println("  5. MySQL is listening on localhost:3306");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
