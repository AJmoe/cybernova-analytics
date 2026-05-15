package com.taskproject.pd_webapp.dao;

import com.taskproject.pd_webapp.model.Testimonial;
import com.taskproject.pd_webapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestimonialDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public void submitTestimonial(Testimonial testimonial) {
        String sql = """
                INSERT INTO testimonials
                (client_id, rating, title, feedback_text, service_type, is_published)
                VALUES (?, ?, ?, ?, ?, 0)
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, testimonial.getClientId());
            stmt.setInt(2, testimonial.getRating());
            stmt.setString(3, testimonial.getTitle());
            stmt.setString(4, testimonial.getFeedbackText());
            stmt.setString(5, testimonial.getServiceType());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error submitting testimonial", e);
        }
    }

    public List<Testimonial> findAll() {
        List<Testimonial> testimonials = new ArrayList<>();

        String sql = """
                SELECT *
                FROM testimonials
                ORDER BY published_date DESC
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                testimonials.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching testimonials", e);
        }

        return testimonials;
    }

    public List<Testimonial> findPending() {
        List<Testimonial> testimonials = new ArrayList<>();

        String sql = """
                SELECT *
                FROM testimonials
                WHERE is_published = 0
                ORDER BY published_date DESC
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                testimonials.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching pending testimonials", e);
        }

        return testimonials;
    }

    public List<Testimonial> findLatestPublished(int limit) {
        List<Testimonial> testimonials = new ArrayList<>();

        String sql = """
                SELECT *
                FROM testimonials
                WHERE is_published = 1
                ORDER BY published_date DESC
                LIMIT ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    testimonials.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching published testimonials", e);
        }

        return testimonials;
    }

    public void approveTestimonial(int testimonialId) {
        updatePublishedStatus(testimonialId, true);
    }

    public void hideTestimonial(int testimonialId) {
        updatePublishedStatus(testimonialId, false);
    }

    public void deleteTestimonial(int testimonialId) {
        String sql = "DELETE FROM testimonials WHERE testimonial_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, testimonialId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting testimonial", e);
        }
    }

    private void updatePublishedStatus(int testimonialId, boolean published) {
        String sql = """
                UPDATE testimonials
                SET is_published = ?
                WHERE testimonial_id = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, published);
            stmt.setInt(2, testimonialId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating testimonial status", e);
        }
    }

    private Testimonial mapRow(ResultSet rs) throws SQLException {
        Testimonial testimonial = new Testimonial();

        testimonial.setTestimonialId(rs.getInt("testimonial_id"));
        testimonial.setClientId(rs.getInt("client_id"));
        testimonial.setRating(rs.getInt("rating"));
        testimonial.setTitle(rs.getString("title"));
        testimonial.setFeedbackText(rs.getString("feedback_text"));
        testimonial.setServiceType(rs.getString("service_type"));
        testimonial.setPublishedDate(rs.getTimestamp("published_date"));
        testimonial.setPublished(rs.getBoolean("is_published"));

        return testimonial;
    }
}