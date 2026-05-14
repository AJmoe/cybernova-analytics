package com.taskproject.pd_webapp.dao;
import com.taskproject.pd_webapp.model.AdminUser;
import com.taskproject.pd_webapp.util.DBConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
/**
 * DAO for AdminUser database operations
 * Handles authentication, password verification, and user management
 * Uses SHA-256 with salt for password hashing
 */
public class AdminUserDAO {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    public AdminUser authenticateAdmin(String username, String plainPassword) {
        if (username == null || plainPassword == null) {
            return null;
        }
        String sql = "SELECT admin_id, username, password_hash, email, full_name, is_active FROM admin_users WHERE username = ? AND is_active = TRUE LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (verifyPassword(plainPassword, storedHash)) {
                        AdminUser user = new AdminUser();
                        user.setAdminId(rs.getInt("admin_id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setFullName(rs.getString("full_name"));
                        user.setActive(rs.getBoolean("is_active"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public AdminUser findByUsername(String username) {
        if (username == null) {
            return null;
        }
        String sql = "SELECT admin_id, username, email, full_name, is_active FROM admin_users WHERE username = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AdminUser user = new AdminUser();
                    user.setAdminId(rs.getInt("admin_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("full_name"));
                    user.setActive(rs.getBoolean("is_active"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdminUser findByEmail(String email) {
        if (email == null) {
            return null;
        }
        String sql = "SELECT admin_id, username, email, full_name, is_active FROM admin_users WHERE LOWER(email) = LOWER(?) LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AdminUser user = new AdminUser();
                    user.setAdminId(rs.getInt("admin_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("full_name"));
                    user.setActive(rs.getBoolean("is_active"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    public int createAdminUser(AdminUser adminUser) {
        if (adminUser == null) {
            throw new IllegalArgumentException("Admin user cannot be null");
        }

        String sql = "INSERT INTO admin_users (username, password_hash, email, full_name, is_active) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, adminUser.getUsername());
            stmt.setString(2, adminUser.getPasswordHash());
            stmt.setString(3, adminUser.getEmail());
            stmt.setString(4, adminUser.getFullName());
            stmt.setBoolean(5, adminUser.isActive());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating admin user", e);
        }
    }

    public static String hashPassword(String plainPassword) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);
            String hashString = hashWithSalt(plainPassword, salt);
            return saltString + "$" + hashString;
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }
    private static boolean verifyPassword(String plainPassword, String storedHash) {
        try {
            if (storedHash == null || !storedHash.contains("$")) {
                return false;
            }
            String[] parts = storedHash.split("\\$");
            if (parts.length != 2) {
                return false;
            }
            String saltString = parts[0];
            String storedHashString = parts[1];
            byte[] salt = Base64.getDecoder().decode(saltString);
            String computedHash = hashWithSalt(plainPassword, salt);
            return computedHash.equals(storedHashString);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static String hashWithSalt(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
