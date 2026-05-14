package com.taskproject.pd_webapp.dao;

import com.taskproject.pd_webapp.model.WorkshopEvent;
import com.taskproject.pd_webapp.model.WorkshopGallery;
import com.taskproject.pd_webapp.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO to fetch workshop events and their gallery images grouped together.
 */
public class GalleryDAO {

    public static class GalleryImageRow {
        private final int galleryId;
        private final int eventId;
        private final String eventTitle;
        private final String imageUrl;
        private final String imageTitle;

        public GalleryImageRow(int galleryId, int eventId, String eventTitle, String imageUrl, String imageTitle) {
            this.galleryId = galleryId;
            this.eventId = eventId;
            this.eventTitle = eventTitle;
            this.imageUrl = imageUrl;
            this.imageTitle = imageTitle;
        }

        public int getGalleryId() {
            return galleryId;
        }

        public int getEventId() {
            return eventId;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageTitle() {
            return imageTitle;
        }
    }

    public List<WorkshopGallery> findAllEventsWithImages() {
        List<WorkshopGallery> events = new ArrayList<>();

        String sql = """
                SELECT we.event_id, we.title, we.description, we.event_date, we.location,
                       GROUP_CONCAT(wg.image_url ORDER BY IFNULL(wg.display_order,0) SEPARATOR ',') AS images
                FROM workshop_events we
                LEFT JOIN workshop_gallery wg ON we.event_id = wg.event_id
                WHERE we.is_active = 1
                GROUP BY we.event_id
                ORDER BY we.event_date DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WorkshopGallery ev = new WorkshopGallery();
                ev.setTitle(rs.getString("title"));
                ev.setDescription(rs.getString("description"));
                String eventDate = rs.getString("event_date");
                ev.setEventDate(eventDate == null ? "" : eventDate);
                ev.setLocation(rs.getString("location"));
                ev.setImageUrls(rs.getString("images"));
                events.add(ev);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error loading gallery events", e);
        }

        return events;
    }

    public void addImageToEvent(int eventId, String imageUrl, String imageTitle, int displayOrder) {
        String sql = """
                INSERT INTO workshop_gallery (event_id, image_url, image_title, display_order)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setString(2, imageUrl);
            ps.setString(3, imageTitle == null || imageTitle.isEmpty() ? "Image" : imageTitle);
            ps.setInt(4, displayOrder);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding image to gallery", e);
        }
    }

    public List<WorkshopEvent> findActiveEvents() {
        List<WorkshopEvent> events = new ArrayList<>();
        String sql = "SELECT event_id, title, description, event_date, location, capacity, registered_count, instructor, event_type, is_active "
                + "FROM workshop_events WHERE is_active = 1 ORDER BY event_date DESC, title ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WorkshopEvent event = new WorkshopEvent();
                event.setEventId(rs.getInt("event_id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                Timestamp eventDate = rs.getTimestamp("event_date");
                event.setEventDate(eventDate == null ? null : eventDate.toLocalDateTime());
                event.setLocation(rs.getString("location"));
                event.setCapacity(rs.getInt("capacity"));
                event.setRegisteredCount(rs.getInt("registered_count"));
                event.setInstructor(rs.getString("instructor"));
                event.setEventType(rs.getString("event_type"));
                event.setActive(rs.getBoolean("is_active"));
                events.add(event);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching active workshop events", e);
        }

        return events;
    }

    public int createWorkshopEvent(String title, String description, LocalDateTime eventDate,
                                   String location, Integer capacity, String instructor, String eventType) {
        String sql = """
                INSERT INTO workshop_events
                    (title, description, event_date, location, capacity, registered_count, instructor, event_type, is_active)
                VALUES (?, ?, ?, ?, ?, 0, ?, ?, 1)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setTimestamp(3, Timestamp.valueOf(eventDate));
            ps.setString(4, location);

            if (capacity == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, capacity);
            }

            ps.setString(6, instructor);
            ps.setString(7, eventType);
            ps.executeUpdate();

            try (ResultSet generated = ps.getGeneratedKeys()) {
                if (generated.next()) {
                    return generated.getInt(1);
                }
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating workshop event", e);
        }
    }

    public void deactivateWorkshopEvent(int eventId) {
        String sql = "UPDATE workshop_events SET is_active = 0 WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deactivating workshop event", e);
        }
    }

    public List<GalleryImageRow> findGalleryImagesForAdmin() {
        List<GalleryImageRow> images = new ArrayList<>();
        String sql = """
                SELECT wg.gallery_id, wg.event_id, we.title AS event_title, wg.image_url, wg.image_title
                FROM workshop_gallery wg
                INNER JOIN workshop_events we ON we.event_id = wg.event_id
                ORDER BY wg.upload_date DESC, wg.gallery_id DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                images.add(new GalleryImageRow(
                        rs.getInt("gallery_id"),
                        rs.getInt("event_id"),
                        rs.getString("event_title"),
                        rs.getString("image_url"),
                        rs.getString("image_title")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching gallery images", e);
        }

        return images;
    }

    public void deleteGalleryImage(int galleryId) {
        String sql = "DELETE FROM workshop_gallery WHERE gallery_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, galleryId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting gallery image", e);
        }
    }
}

