package com.taskproject.pd_webapp.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * Validation Utility Class
 * Provides centralized validation methods for all form inputs.
 * Implements security best practices to prevent data injection and malformed input.
 */
public class ValidationUtil {

    // Regex patterns for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z\\s'-]+$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[0-9\\s+()-]+$"
    );

    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_-]{3,20}$"
    );

    // Validation message constants
    public static final String MSG_FIELD_REQUIRED = "This field is required.";
    public static final String MSG_INVALID_NAME = "Name can only contain letters, spaces, hyphens, and apostrophes.";
    public static final String MSG_INVALID_EMAIL = "Please enter a valid email address (e.g., user@example.com).";
    public static final String MSG_INVALID_PHONE = "Please enter a valid phone number (digits, spaces, +, -, () only).";
    public static final String MSG_INVALID_URL = "Please enter a valid URL starting with http:// or https://.";
    public static final String MSG_INVALID_RATING = "Rating must be a number between 1 and 5.";
    public static final String MSG_MIN_LENGTH = "This field must be at least %d characters long.";
    public static final String MSG_MAX_LENGTH = "This field must not exceed %d characters.";
    public static final String MSG_INVALID_USERNAME = "Username must be 3-20 characters (letters, numbers, hyphens, underscores only).";
    public static final String MSG_INVALID_PASSWORD = "Password must be at least 8 characters long.";
    public static final String MSG_PASSWORDS_DONT_MATCH = "Passwords do not match.";
    public static final String MSG_INVALID_INTEGER = "This field must contain a valid number.";

    /**
     * Validates that a field is not blank/empty
     */
    public static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates that a name contains only letters, spaces, hyphens, and apostrophes
     */
    public static boolean isValidName(String name) {
        if (!isRequired(name)) return false;
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        if (!isRequired(email)) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates phone number format
     * Allows: digits, spaces, +, -, ()
     */
    public static boolean isValidPhone(String phone) {
        if (!isRequired(phone)) return false;
        String trimmed = phone.trim();
        // Phone must be at least 10 characters after removing spaces
        if (trimmed.replaceAll("\\s", "").length() < 10) return false;
        return PHONE_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Validates username format
     * Must be 3-20 characters, alphanumeric with hyphens and underscores
     */
    public static boolean isValidUsername(String username) {
        if (!isRequired(username)) return false;
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    /**
     * Validates password meets minimum requirements
     * Must be at least 8 characters
     */
    public static boolean isValidPassword(String password) {
        if (!isRequired(password)) return false;
        return password.length() >= 8;
    }

    /**
     * Validates that two passwords match
     */
    public static boolean passwordsMatch(String password, String confirmPassword) {
        if (!isRequired(password) || !isRequired(confirmPassword)) return false;
        return password.equals(confirmPassword);
    }

    /**
     * Validates a URL format
     * Must start with http:// or https://
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return true; // URL is optional in most forms
        }
        try {
            new URI(url.trim()).toURL();
            String lower = url.trim().toLowerCase();
            return lower.startsWith("http://") || lower.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates a rating is between 1-5
     */
    public static boolean isValidRating(String ratingStr) {
        if (!isRequired(ratingStr)) return false;
        try {
            int rating = Integer.parseInt(ratingStr.trim());
            return rating >= 1 && rating <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates minimum text length
     */
    public static boolean hasMinLength(String value, int minLength) {
        if (value == null) return false;
        return value.trim().length() >= minLength;
    }

    /**
     * Validates maximum text length
     */
    public static boolean hasMaxLength(String value, int maxLength) {
        if (value == null) return true;
        return value.trim().length() <= maxLength;
    }

    /**
     * Validates an integer value
     */
    public static boolean isValidInteger(String value) {
        if (!isRequired(value)) return false;
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates an integer is within range
     */
    public static boolean isIntegerInRange(String value, int min, int max) {
        if (!isValidInteger(value)) return false;
        int num = Integer.parseInt(value.trim());
        return num >= min && num <= max;
    }

    /**
     * Safely trims a string (null-safe)
     */
    public static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * Checks if a string is blank (null or empty after trim)
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates a security request form
     */
    public static String validateSecurityRequest(String name, String email, String phone, 
                                                   String organization, String country, 
                                                   String jobTitle, String issueType, String description) {
        if (!isRequired(name)) return "Name is required.";
        if (!isValidName(name)) return "Name contains invalid characters.";

        if (!isRequired(email)) return "Email is required.";
        if (!isValidEmail(email)) return "Please enter a valid email address.";

        if (!isRequired(phone)) return "Phone is required.";
        if (!isValidPhone(phone)) return "Please enter a valid phone number.";

        if (!isRequired(organization)) return "Organization is required.";
        if (!hasMaxLength(organization, 255)) return "Organization name is too long (max 255 characters).";

        if (!isRequired(country)) return "Country is required.";
        if (!hasMaxLength(country, 100)) return "Country name is too long (max 100 characters).";

        if (!isRequired(jobTitle)) return "Job title is required.";
        if (!hasMaxLength(jobTitle, 100)) return "Job title is too long (max 100 characters).";

        if (!isRequired(issueType)) return "Issue type is required.";
        if (!isValidIssueType(issueType)) return "Invalid issue type selected.";

        if (!isRequired(description)) return "Description is required.";
        if (!hasMinLength(description, 20)) return "Description must be at least 20 characters long.";
        if (!hasMaxLength(description, 5000)) return "Description is too long (max 5000 characters).";

        return null; // No errors
    }

    /**
     * Validates a testimonial form
     */
    public static String validateTestimonial(String name, String email, String phone,
                                              String organization, String country, String jobTitle,
                                              String serviceType, String rating, String title, String feedbackText) {
        if (!isRequired(name)) return "Name is required.";
        if (!isValidName(name)) return "Name contains invalid characters.";

        if (!isRequired(email)) return "Email is required.";
        if (!isValidEmail(email)) return "Please enter a valid email address.";

        if (!isRequired(phone)) return "Phone is required.";
        if (!isValidPhone(phone)) return "Please enter a valid phone number.";

        if (!isRequired(organization)) return "Organization is required.";
        if (!hasMaxLength(organization, 255)) return "Organization name is too long (max 255 characters).";

        if (!isRequired(country)) return "Country is required.";
        if (!hasMaxLength(country, 100)) return "Country name is too long (max 100 characters).";

        if (!isRequired(jobTitle)) return "Job title is required.";
        if (!hasMaxLength(jobTitle, 100)) return "Job title is too long (max 100 characters).";

        if (!isRequired(serviceType)) return "Service type is required.";

        if (!isRequired(rating)) return "Rating is required.";
        if (!isValidRating(rating)) return "Rating must be a number between 1 and 5.";

        if (!isRequired(title)) return "Testimonial title is required.";
        if (!hasMinLength(title, 5)) return "Title must be at least 5 characters long.";
        if (!hasMaxLength(title, 200)) return "Title is too long (max 200 characters).";

        if (!isRequired(feedbackText)) return "Feedback is required.";
        if (!hasMinLength(feedbackText, 30)) return "Feedback must be at least 30 characters long.";
        if (!hasMaxLength(feedbackText, 5000)) return "Feedback is too long (max 5000 characters).";

        return null; // No errors
    }

    /**
     * Validates admin login credentials
     */
    public static String validateAdminLogin(String username, String password) {
        if (!isRequired(username)) return "Username is required.";
        if (username.trim().length() < 3) return "Username must be at least 3 characters long.";

        if (!isRequired(password)) return "Password is required.";
        if (password.length() < 1) return "Password is required.";

        return null; // No errors
    }

    /**
     * Validates admin registration form
     */
    public static String validateAdminRegistration(String username, String email, String fullName,
                                                     String password, String confirmPassword) {
        if (!isRequired(username)) return "Username is required.";
        if (!isValidUsername(username)) return "Username must be 3-20 characters (letters, numbers, hyphens, underscores only).";

        if (!isRequired(email)) return "Email is required.";
        if (!isValidEmail(email)) return "Please enter a valid email address.";

        if (!isRequired(fullName)) return "Full name is required.";
        if (!isValidName(fullName)) return "Full name contains invalid characters.";
        if (!hasMaxLength(fullName, 255)) return "Full name is too long (max 255 characters).";

        if (!isRequired(password)) return "Password is required.";
        if (!isValidPassword(password)) return "Password must be at least 8 characters long.";

        if (!isRequired(confirmPassword)) return "Password confirmation is required.";
        if (!passwordsMatch(password, confirmPassword)) return "Passwords do not match.";

        return null; // No errors
    }

    /**
     * Validates content card form
     */
    public static String validateContentCard(String pageType, String title, String description,
                                              String imageUrl, String externalLink) {
        if (!isRequired(pageType)) return "Page type is required.";
        if (!isValidPageType(pageType)) return "Invalid page type selected.";

        if (!isRequired(title)) return "Card title is required.";
        if (!hasMinLength(title, 3)) return "Title must be at least 3 characters long.";
        if (!hasMaxLength(title, 200)) return "Title is too long (max 200 characters).";

        if (!isRequired(description)) return "Description is required.";
        if (!hasMinLength(description, 10)) return "Description must be at least 10 characters long.";
        if (!hasMaxLength(description, 2000)) return "Description is too long (max 2000 characters).";

        if (!isBlank(imageUrl) && !isValidUrl(imageUrl)) {
            return "Please enter a valid image URL starting with http:// or https://.";
        }

        if (!isBlank(externalLink) && !isValidUrl(externalLink)) {
            return "Please enter a valid external link starting with http:// or https://.";
        }

        return null; // No errors
    }

    /**
     * Validates workshop event form
     */
    public static String validateWorkshopEvent(String title, String description, String location,
                                                String capacity, String instructor, String eventType) {
        if (!isRequired(title)) return "Event title is required.";
        if (!hasMinLength(title, 3)) return "Title must be at least 3 characters long.";
        if (!hasMaxLength(title, 255)) return "Title is too long (max 255 characters).";

        if (!isRequired(description)) return "Description is required.";
        if (!hasMinLength(description, 10)) return "Description must be at least 10 characters long.";
        if (!hasMaxLength(description, 2000)) return "Description is too long (max 2000 characters).";

        if (!isBlank(location) && !hasMaxLength(location, 200)) {
            return "Location is too long (max 200 characters).";
        }

        if (!isBlank(capacity)) {
            if (!isValidInteger(capacity)) return "Capacity must be a valid number.";
            try {
                int cap = Integer.parseInt(capacity.trim());
                if (cap < 0) return "Capacity cannot be negative.";
            } catch (NumberFormatException e) {
                return "Capacity must be a valid number.";
            }
        }

        if (!isBlank(instructor) && !hasMaxLength(instructor, 200)) {
            return "Instructor name is too long (max 200 characters).";
        }

        if (!isBlank(eventType) && !hasMaxLength(eventType, 200)) {
            return "Event type is too long (max 200 characters).";
        }

        return null; // No errors
    }

    /**
     * Validates gallery image form
     */
    public static String validateGalleryImage(String eventId, String imageUrl, String imageTitle) {
        if (!isRequired(eventId)) return "Workshop event is required.";
        if (!isValidInteger(eventId)) return "Invalid event selected.";

        if (!isRequired(imageUrl)) return "Image URL is required.";
        if (!isValidUrl(imageUrl)) return "Please enter a valid image URL starting with http:// or https://.";

        if (!isBlank(imageTitle) && !hasMaxLength(imageTitle, 255)) {
            return "Image title is too long (max 255 characters).";
        }

        return null; // No errors
    }

    /**
     * Validates that page type is one of the allowed values
     */
    private static boolean isValidPageType(String pageType) {
        return "SOLUTION".equals(pageType) || 
               "CASE_STUDY".equals(pageType) || 
               "BLOG".equals(pageType);
    }

    /**
     * Validates that issue type is one of the allowed values
     */
    private static boolean isValidIssueType(String issueType) {
        return "Phishing Response".equals(issueType) ||
               "Incident Response".equals(issueType) ||
               "Vulnerability Assessment".equals(issueType) ||
               "Cloud Hardening".equals(issueType) ||
               "Security Awareness Training".equals(issueType) ||
               "Compliance Review".equals(issueType);
    }
}

