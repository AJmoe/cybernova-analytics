package com.taskproject.pd_webapp.web.servlet;


import com.taskproject.pd_webapp.dao.ContentCardDAO;
import com.taskproject.pd_webapp.model.ContentCard;
import com.taskproject.pd_webapp.util.ValidationUtil;
import com.taskproject.pd_webapp.util.HTMLSanitizer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/content-cards/add")
public class AddContentCardServlet extends HttpServlet {

    private final ContentCardDAO contentCardDAO = new ContentCardDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageType = ValidationUtil.safeTrim(request.getParameter("pageType"));
        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        String imageUrl = ValidationUtil.safeTrim(request.getParameter("imageUrl"));
        String externalLink = ValidationUtil.safeTrim(request.getParameter("externalLink"));
        String status = ValidationUtil.safeTrim(request.getParameter("status"));

        // Validate all inputs
        String validationError = ValidationUtil.validateContentCard(
            pageType, title, description, imageUrl, externalLink
        );

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
            return;
        }

        // Set default status if not provided
        if (ValidationUtil.isBlank(status)) {
            status = "PUBLISHED";
        }

        // Validate status value
        if (!status.equals("PUBLISHED") && !status.equals("HIDDEN")) {
            status = "PUBLISHED";
        }

        try {
            // Sanitize text fields to prevent XSS attacks
            String sanitizedTitle = HTMLSanitizer.sanitizeTextField(title);
            String sanitizedDescription = HTMLSanitizer.sanitizeTextarea(description);

            ContentCard card = new ContentCard();
            card.setPageType(pageType);
            card.setTitle(sanitizedTitle);
            card.setDescription(sanitizedDescription);
            card.setImageUrl(ValidationUtil.isBlank(imageUrl) ? null : imageUrl);
            card.setExternalLink(ValidationUtil.isBlank(externalLink) ? null : externalLink);
            card.setStatus(status);

            contentCardDAO.addCard(card);

            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?success=cardAdded");
        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to add card. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
        }
    }
}