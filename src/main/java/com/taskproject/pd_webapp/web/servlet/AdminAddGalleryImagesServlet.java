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
import java.util.List;

@WebServlet("/admin/gallery/add-images")
public class AdminAddGalleryImagesServlet extends HttpServlet {
    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<WorkshopEvent> events = galleryDAO.findActiveEvents();
        request.setAttribute("events", events);
        request.setAttribute("pageTitle", "Add Gallery Images - CyberNova Admin");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/add-gallery-images.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventIdStr = ValidationUtil.safeTrim(request.getParameter("eventId"));
        String imageUrl = ValidationUtil.safeTrim(request.getParameter("imageUrl"));
        String imageTitle = ValidationUtil.safeTrim(request.getParameter("imageTitle"));
        String displayOrderStr = ValidationUtil.safeTrim(request.getParameter("displayOrder"));

        // Validate all inputs
        String validationError = ValidationUtil.validateGalleryImage(eventIdStr, imageUrl, imageTitle);

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/gallery/add-images?error=" + encodedError);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);
            int displayOrder = 0;
            
            if (!ValidationUtil.isBlank(displayOrderStr)) {
                if (!ValidationUtil.isValidInteger(displayOrderStr)) {
                    String encodedError = URLEncoder.encode("Display order must be a valid number.", StandardCharsets.UTF_8);
                    response.sendRedirect(request.getContextPath()
                            + "/admin/gallery/add-images?error=" + encodedError);
                    return;
                }
                displayOrder = Integer.parseInt(displayOrderStr);
                if (displayOrder < 0) {
                    String encodedError = URLEncoder.encode("Display order cannot be negative.", StandardCharsets.UTF_8);
                    response.sendRedirect(request.getContextPath()
                            + "/admin/gallery/add-images?error=" + encodedError);
                    return;
                }
            }

            // Sanitize image title to prevent XSS attacks
            String sanitizedImageTitle = ValidationUtil.isBlank(imageTitle) ? 
                    "" : HTMLSanitizer.sanitizeTextField(imageTitle);

            galleryDAO.addImageToEvent(eventId, imageUrl, sanitizedImageTitle, displayOrder);

            response.sendRedirect(request.getContextPath()
                    + "/admin/gallery/add-images?success=imageAdded");
        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/gallery/add-images?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to add image. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/gallery/add-images?error=" + encodedError);
        }
    }
}
