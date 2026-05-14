package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.model.SecurityRequest;
import com.taskproject.pd_webapp.service.SecurityRequestRepository;
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

@WebServlet(urlPatterns = {"/submit-inquiry", "/submit-security-request"})
public class InquirySubmitServlet extends HttpServlet {
    private final SecurityRequestRepository repository = SecurityRequestRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = ValidationUtil.safeTrim(request.getParameter("name"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String phone = ValidationUtil.safeTrim(request.getParameter("phone"));
        String organization = ValidationUtil.safeTrim(request.getParameter("organization"));
        String country = ValidationUtil.safeTrim(request.getParameter("country"));
        String jobTitle = ValidationUtil.safeTrim(request.getParameter("jobTitle"));
        String issueType = ValidationUtil.safeTrim(request.getParameter("issueType"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));

        // Validate all inputs
        String validationError = ValidationUtil.validateSecurityRequest(
            name, email, phone, organization, country, jobTitle, issueType, description
        );

        if (validationError != null) {
            String encodedError = URLEncoder.encode(validationError, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?error=" + encodedError);
            return;
        }

        try {
            // Sanitize text fields to prevent XSS attacks
            String sanitizedName = HTMLSanitizer.sanitizeTextField(name);
            String sanitizedOrganization = HTMLSanitizer.sanitizeTextField(organization);
            String sanitizedCountry = HTMLSanitizer.sanitizeTextField(country);
            String sanitizedJobTitle = HTMLSanitizer.sanitizeTextField(jobTitle);
            String sanitizedDescription = HTMLSanitizer.sanitizeTextarea(description);

            SecurityRequest securityRequest = new SecurityRequest();
            securityRequest.setClientName(sanitizedName);
            securityRequest.setClientEmail(email);
            securityRequest.setClientPhone(phone);
            securityRequest.setOrganization(sanitizedOrganization);
            securityRequest.setCountry(sanitizedCountry);
            securityRequest.setJobTitle(sanitizedJobTitle);
            securityRequest.setTypeOfSecurityIssue(issueType);
            securityRequest.setDescription(sanitizedDescription);
            repository.save(securityRequest);
            response.sendRedirect(request.getContextPath() + "/thank-you");
        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?error=" + encodedError);
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("Failed to submit security request. Please try again.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?error=" + encodedError);
        }
    }
}
