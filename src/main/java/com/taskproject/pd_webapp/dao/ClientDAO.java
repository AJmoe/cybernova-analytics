package com.taskproject.pd_webapp.dao;

import com.taskproject.pd_webapp.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple DAO for ensuring a client record exists for feedback/testimonial submissions.
 */
public class ClientDAO {

    public int findOrCreateClientId(String name,
                                   String email,
                                   String phone,
                                   String organization,
                                   String country,
                                   String jobTitle) {
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();

        String findSql = "SELECT client_id FROM clients WHERE LOWER(email) = ? LIMIT 1";
        String insertSql = "INSERT INTO clients (name, email, phone, organization, country, job_title) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement findStmt = conn.prepareStatement(findSql)) {
                findStmt.setString(1, normalizedEmail);
                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("client_id");
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, safeTrim(name));
                insertStmt.setString(2, normalizedEmail);
                insertStmt.setString(3, safeTrim(phone));
                insertStmt.setString(4, safeTrim(organization));
                insertStmt.setString(5, safeTrim(country));
                insertStmt.setString(6, safeTrim(jobTitle));
                insertStmt.executeUpdate();

                try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

            throw new IllegalStateException("Unable to create client record");
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to find or create client", e);
        }
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }
}

