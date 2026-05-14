package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.ClientDAO;
import com.taskproject.pd_webapp.dao.TestimonialDAO;
import com.taskproject.pd_webapp.model.Testimonial;
import com.taskproject.pd_webapp.util.ValidationUtil;
import com.taskproject.pd_webapp.util.HTMLSanitizer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/submit-testimonial")
public class SubmitTestimonialServlet extends HttpServlet {

    private final TestimonialDAO testimonialDAO = new TestimonialDAO();
    private final ClientDAO clientDAO = new ClientDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = ValidationUtil.safeTrim(request.getParameter("name"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String phone = ValidationUtil.safeTrim(request.getParameter("phone"));
        String organization = ValidationUtil.safeTrim(request.getParameter("organization"));
        String country = ValidationUtil.safeTrim(request.getParameter("country"));
        String jobTitle = ValidationUtil.safeTrim(request.getParameter("jobTitle"));
        String serviceType = ValidationUtil.safeTrim(request.getParameter("serviceType"));
        String ratingParam = ValidationUtil.safeTrim(request.getParameter("rating"));
        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String feedbackText = ValidationUtil.safeTrim(request.getParameter("feedbackText"));

        // Validate all inputs
        String validationError = ValidationUtil.validateTestimonial(
            name, email, phone, organization, country, jobTitle, serviceType, ratingParam, title, feedbackText
        );

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?testimonialError=" + encodedError);
            return;
        }

        try {
            int rating = Integer.parseInt(ratingParam);

            // Sanitize text fields to prevent XSS attacks
            String sanitizedName = HTMLSanitizer.sanitizeTextField(name);
            String sanitizedOrganization = HTMLSanitizer.sanitizeTextField(organization);
            String sanitizedCountry = HTMLSanitizer.sanitizeTextField(country);
            String sanitizedJobTitle = HTMLSanitizer.sanitizeTextField(jobTitle);
            String sanitizedTitle = HTMLSanitizer.sanitizeTextField(title);
            String sanitizedFeedback = HTMLSanitizer.sanitizeTextarea(feedbackText);

            int clientId = clientDAO.findOrCreateClientId(
                sanitizedName, email, phone, sanitizedOrganization, sanitizedCountry, sanitizedJobTitle
            );

            Testimonial testimonial = new Testimonial();
            testimonial.setClientId(clientId);
            testimonial.setRating(rating);
            testimonial.setTitle(sanitizedTitle);
            testimonial.setFeedbackText(sanitizedFeedback);
            testimonial.setServiceType(serviceType);
            testimonial.setPublished(false); // admin must approve first

            testimonialDAO.submitTestimonial(testimonial);

            response.sendRedirect(request.getContextPath() + "/contact?testimonialSuccess=submitted");

        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?testimonialError=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to submit testimonial. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?testimonialError=" + encodedError);
        }
    }
}