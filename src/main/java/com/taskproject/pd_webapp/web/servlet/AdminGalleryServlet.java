package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.GalleryDAO;
import com.taskproject.pd_webapp.model.WorkshopEvent;
import com.taskproject.pd_webapp.util.ValidationUtil;
import com.taskproject.pd_webapp.util.HTMLSanitizer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/admin/gallery")
public class AdminGalleryServlet extends HttpServlet {
    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<WorkshopEvent> events = galleryDAO.findActiveEvents();
        request.setAttribute("events", events);
        request.setAttribute("galleryImages", galleryDAO.findGalleryImagesForAdmin());
        request.setAttribute("pageTitle", "Manage Gallery - CyberNova Admin");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/gallery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = ValidationUtil.safeTrim(request.getParameter("action"));

        try {
            switch (action) {
                case "createEvent" -> handleCreateEvent(request, response);
                case "deactivateEvent" -> handleDeactivateEvent(request, response);
                case "deleteImage" -> handleDeleteImage(request, response);
                default -> {
                    String encodedError = URLEncoder.encode("Invalid action.", StandardCharsets.UTF_8);
                    response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
                }
            }
        } catch (NumberFormatException e) {
            String encodedError = URLEncoder.encode("Invalid number format provided.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("An error occurred. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        }
    }

    private void handleCreateEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        String eventDateValue = ValidationUtil.safeTrim(request.getParameter("eventDate"));
        String location = ValidationUtil.safeTrim(request.getParameter("location"));
        String instructor = ValidationUtil.safeTrim(request.getParameter("instructor"));
        String eventType = ValidationUtil.safeTrim(request.getParameter("eventType"));
        String capacityValue = ValidationUtil.safeTrim(request.getParameter("capacity"));

        // Validate required fields
        String validationError = ValidationUtil.validateWorkshopEvent(
            title, description, location, capacityValue, instructor, eventType
        );

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
            return;
        }

        if (ValidationUtil.isBlank(eventDateValue)) {
            String encodedError = URLEncoder.encode("Event date and time are required.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
            return;
        }

        LocalDateTime eventDate;
        try {
            eventDate = LocalDateTime.parse(eventDateValue);
        } catch (DateTimeParseException e) {
            String encodedError = URLEncoder.encode("Please use a valid date/time format.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
            return;
        }

        Integer capacity = null;
        if (!ValidationUtil.isBlank(capacityValue)) {
            if (!ValidationUtil.isValidInteger(capacityValue)) {
                String encodedError = URLEncoder.encode("Capacity must be a valid number.", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
                return;
            }
            capacity = Integer.parseInt(capacityValue);
            if (capacity < 0) {
                String encodedError = URLEncoder.encode("Capacity cannot be negative.", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
                return;
            }
        }

        try {
            // Sanitize text fields to prevent XSS attacks
            String sanitizedTitle = HTMLSanitizer.sanitizeTextField(title);
            String sanitizedDescription = HTMLSanitizer.sanitizeTextarea(description);
            String sanitizedLocation = ValidationUtil.isBlank(location) ? "" : HTMLSanitizer.sanitizeTextField(location);
            String sanitizedInstructor = ValidationUtil.isBlank(instructor) ? "" : HTMLSanitizer.sanitizeTextField(instructor);
            String sanitizedEventType = ValidationUtil.isBlank(eventType) ? "" : HTMLSanitizer.sanitizeTextField(eventType);

            galleryDAO.createWorkshopEvent(
                sanitizedTitle, sanitizedDescription, eventDate, 
                sanitizedLocation, capacity, sanitizedInstructor, sanitizedEventType
            );
            response.sendRedirect(request.getContextPath() + "/admin/gallery?success=eventCreated");
        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to create event. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        }
    }

    private void handleDeactivateEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String eventIdParam = ValidationUtil.safeTrim(request.getParameter("eventId"));

        if (!ValidationUtil.isValidInteger(eventIdParam)) {
            String encodedError = URLEncoder.encode("Invalid event ID.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            galleryDAO.deactivateWorkshopEvent(eventId);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?success=eventRemoved");
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to remove event. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        }
    }

    private void handleDeleteImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String galleryIdParam = ValidationUtil.safeTrim(request.getParameter("galleryId"));

        if (!ValidationUtil.isValidInteger(galleryIdParam)) {
            String encodedError = URLEncoder.encode("Invalid gallery image ID.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
            return;
        }

        try {
            int galleryId = Integer.parseInt(galleryIdParam);
            galleryDAO.deleteGalleryImage(galleryId);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?success=imageRemoved");
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to delete image. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/gallery?error=" + encodedError);
        }
    }
}
