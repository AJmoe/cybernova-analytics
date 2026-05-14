package com.taskproject.pd_webapp.util;

/**
 * HTML Input Sanitization Utility
 * Escapes HTML characters to prevent XSS (Cross-Site Scripting) attacks
 * Ensures user input is safe to display in HTML context
 */
public class HTMLSanitizer {

    /**
     * Escapes HTML special characters to prevent XSS attacks
     * Converts: < > & " ' into HTML entities
     * 
     * @param input User input string
     * @return Escaped string safe for HTML display
     */
    public static String escapeHtml(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input
                .replace("&", "&amp;")      // & → &amp;
                .replace("<", "&lt;")       // < → &lt;
                .replace(">", "&gt;")       // > → &gt;
                .replace("\"", "&quot;")    // " → &quot;
                .replace("'", "&#39;");     // ' → &#39;
    }

    /**
     * Escapes HTML and trims whitespace
     * Best for text that comes from users
     * 
     * @param input User input string
     * @return Trimmed and escaped string
     */
    public static String escapeAndTrim(String input) {
        if (input == null) {
            return "";
        }
        return escapeHtml(input.trim());
    }

    /**
     * Checks if input contains dangerous HTML/JavaScript
     * 
     * @param input User input to check
     * @return true if dangerous content detected
     */
    public static boolean containsDangerousContent(String input) {
        if (input == null) {
            return false;
        }

        String lower = input.toLowerCase();
        
        // Check for script tags
        if (lower.contains("<script") || lower.contains("</script")) {
            return true;
        }
        
        // Check for event handlers
        if (lower.contains("onclick") || lower.contains("onload") || 
            lower.contains("onerror") || lower.contains("onmouseover")) {
            return true;
        }
        
        // Check for iframes
        if (lower.contains("<iframe") || lower.contains("</iframe")) {
            return true;
        }

        // Check for object/embed
        if (lower.contains("<object") || lower.contains("<embed")) {
            return true;
        }

        // Check for javascript protocol
        if (lower.contains("javascript:")) {
            return true;
        }

        // Check for data protocol
        if (lower.contains("data:text/html")) {
            return true;
        }

        return false;
    }

    /**
     * Sanitizes database input - validates and escapes
     * Use this for data going INTO the database
     * 
     * @param input Raw user input
     * @param fieldName Name of field (for logging)
     * @return Sanitized string safe for database storage
     * @throws IllegalArgumentException if dangerous content detected
     */
    public static String sanitizeForDatabase(String input, String fieldName) {
        if (input == null) {
            return "";
        }

        String trimmed = input.trim();

        // Check for dangerous content
        if (containsDangerousContent(trimmed)) {
            throw new IllegalArgumentException(
                    "HTML/JavaScript code detected in " + fieldName + " field. Not allowed."
            );
        }

        // Escape HTML for safe storage
        return escapeHtml(trimmed);
    }

    /**
     * Sanitizes for HTML display - escapes HTML
     * Use this when displaying user data in HTML
     * 
     * @param input User input to display
     * @return Escaped string safe for HTML rendering
     */
    public static String sanitizeForDisplay(String input) {
        if (input == null) {
            return "";
        }
        return escapeHtml(input);
    }

    /**
     * Remove HTML tags completely (for plain text fields)
     * 
     * @param input User input with possible HTML
     * @return Plain text without HTML tags
     */
    public static String stripHtmlTags(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<[^>]*>", "").trim();
    }

    /**
     * Limit string length and sanitize
     * 
     * @param input User input
     * @param maxLength Maximum allowed length
     * @return Sanitized and trimmed string
     */
    public static String sanitizeWithMaxLength(String input, int maxLength) {
        if (input == null) {
            return "";
        }

        String sanitized = escapeAndTrim(input);
        if (sanitized.length() > maxLength) {
            return sanitized.substring(0, maxLength);
        }

        return sanitized;
    }

    /**
     * Sanitize text field (name, organization, job title, country)
     * For text-only fields that should not contain HTML
     * 
     * @param input User input
     * @return Sanitized text
     */
    public static String sanitizeTextField(String input) {
        if (input == null) {
            return "";
        }

        // Check for dangerous content
        if (containsDangerousContent(input)) {
            throw new IllegalArgumentException("HTML/JavaScript not allowed in this field.");
        }

        // Escape HTML
        return escapeAndTrim(input);
    }

    /**
     * Sanitize textarea (description, feedback, etc.)
     * Allows more content but still prevents XSS
     * 
     * @param input User input
     * @return Sanitized text
     */
    public static String sanitizeTextarea(String input) {
        if (input == null) {
            return "";
        }

        // Check for dangerous content
        if (containsDangerousContent(input)) {
            throw new IllegalArgumentException("HTML/JavaScript not allowed in this field.");
        }

        // Escape HTML but preserve whitespace
        return escapeHtml(input.trim());
    }

    /**
     * Test if string is safe (no special characters that need escaping)
     * 
     * @param input User input to test
     * @return true if no escaping needed
     */
    public static boolean isSafe(String input) {
        if (input == null) {
            return true;
        }

        return !input.contains("&") &&
               !input.contains("<") &&
               !input.contains(">") &&
               !input.contains("\"") &&
               !input.contains("'");
    }

    /**
     * Example usage and testing
     */
    public static void main(String[] args) {
        // Test cases
        String[] testInputs = {
            "John O'Brien",
            "Company & Associates",
            "Manager <role>",
            "<script>alert('xss')</script>",
            "United States",
            "onclick='alert(1)'"
        };

        System.out.println("=== HTML Sanitization Tests ===\n");

        for (String input : testInputs) {
            System.out.println("Input:  " + input);
            System.out.println("Safe:   " + isSafe(input));
            System.out.println("Danger: " + containsDangerousContent(input));
            System.out.println("Output: " + escapeHtml(input));
            System.out.println();
        }
    }
}

