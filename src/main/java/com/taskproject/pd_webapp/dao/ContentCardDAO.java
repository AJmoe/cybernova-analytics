package com.taskproject.pd_webapp.dao;

import com.taskproject.pd_webapp.model.ContentCard;
import com.taskproject.pd_webapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContentCardDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public void addCard(ContentCard card) {
        String sql = """
                INSERT INTO content_cards
                (page_type, title, description, image_url, external_link, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, card.getPageType());
            stmt.setString(2, card.getTitle());
            stmt.setString(3, card.getDescription());
            stmt.setString(4, card.getImageUrl());
            stmt.setString(5, card.getExternalLink());
            stmt.setString(6, card.getStatus() == null ? "PUBLISHED" : card.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding content card", e);
        }
    }

    public List<ContentCard> findAll() {
        List<ContentCard> cards = new ArrayList<>();

        String sql = """
                SELECT *
                FROM content_cards
                ORDER BY created_at DESC
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cards.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all content cards", e);
        }

        return cards;
    }

    public List<ContentCard> findByPageType(String pageType) {
        List<ContentCard> cards = new ArrayList<>();

        String sql = """
                SELECT *
                FROM content_cards
                WHERE page_type = ?
                ORDER BY created_at DESC
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pageType);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cards.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching content cards by page type", e);
        }

        return cards;
    }

    public List<ContentCard> findLatestPublishedByPageType(String pageType, int limit) {
        List<ContentCard> cards = new ArrayList<>();

        String sql = """
                SELECT *
                FROM content_cards
                WHERE page_type = ?
                  AND status = 'PUBLISHED'
                ORDER BY created_at DESC
                LIMIT ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pageType);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cards.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching latest published cards", e);
        }

        return cards;
    }

    public void deleteCard(int cardId) {
        String sql = "DELETE FROM content_cards WHERE card_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cardId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting content card", e);
        }
    }

    public void updateStatus(int cardId, String status) {
        String sql = "UPDATE content_cards SET status = ? WHERE card_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, cardId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating content card status", e);
        }
    }
    public ContentCard findById(int cardId) {
        String sql = "SELECT * FROM content_cards WHERE card_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cardId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding content card by ID", e);
        }

        return null;
    }

    public void updateCard(ContentCard card) {
        String sql = """
            UPDATE content_cards
            SET page_type = ?,
                title = ?,
                description = ?,
                image_url = ?,
                external_link = ?,
                status = ?
            WHERE card_id = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, card.getPageType());
            stmt.setString(2, card.getTitle());
            stmt.setString(3, card.getDescription());
            stmt.setString(4, card.getImageUrl());
            stmt.setString(5, card.getExternalLink());
            stmt.setString(6, card.getStatus());
            stmt.setInt(7, card.getCardId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating content card", e);
        }
    }

    private ContentCard mapRow(ResultSet rs) throws SQLException {
        ContentCard card = new ContentCard();

        card.setCardId(rs.getInt("card_id"));
        card.setPageType(rs.getString("page_type"));
        card.setTitle(rs.getString("title"));
        card.setDescription(rs.getString("description"));
        card.setImageUrl(rs.getString("image_url"));
        card.setExternalLink(rs.getString("external_link"));
        card.setStatus(rs.getString("status"));
        card.setCreatedAt(rs.getTimestamp("created_at"));

        return card;
    }


}