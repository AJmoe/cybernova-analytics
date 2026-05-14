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

@WebServlet("/admin/content-cards/update")
public class UpdateContentCardServlet extends HttpServlet {

    private final ContentCardDAO contentCardDAO = new ContentCardDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cardIdParam = ValidationUtil.safeTrim(request.getParameter("cardId"));
        String pageType = ValidationUtil.safeTrim(request.getParameter("pageType"));
        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        String imageUrl = ValidationUtil.safeTrim(request.getParameter("imageUrl"));
        String externalLink = ValidationUtil.safeTrim(request.getParameter("externalLink"));
        String status = ValidationUtil.safeTrim(request.getParameter("status"));

        // Validate card ID
        if (ValidationUtil.isBlank(cardIdParam)) {
            String encodedError = URLEncoder.encode("Missing card ID.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
            return;
        }

        if (!ValidationUtil.isValidInteger(cardIdParam)) {
            String encodedError = URLEncoder.encode("Invalid card ID.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
            return;
        }

        // Validate content card fields
        String validationError = ValidationUtil.validateContentCard(
            pageType, title, description, imageUrl, externalLink
        );

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
            return;
        }

        try {
            int cardId = Integer.parseInt(cardIdParam);

            // Sanitize text fields to prevent XSS attacks
            String sanitizedTitle = HTMLSanitizer.sanitizeTextField(title);
            String sanitizedDescription = HTMLSanitizer.sanitizeTextarea(description);

            ContentCard card = new ContentCard();
            card.setCardId(cardId);
            card.setPageType(pageType);
            card.setTitle(sanitizedTitle);
            card.setDescription(sanitizedDescription);
            card.setImageUrl(ValidationUtil.isBlank(imageUrl) ? null : imageUrl);
            card.setExternalLink(ValidationUtil.isBlank(externalLink) ? null : externalLink);
            card.setStatus(isValidStatus(status) ? status : "PUBLISHED");

            contentCardDAO.updateCard(card);

            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?success=cardUpdated");

        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to update card. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=" + encodedError);
        }
    }

    private boolean isValidStatus(String status) {
        return "PUBLISHED".equals(status) || "HIDDEN".equals(status);
    }
}