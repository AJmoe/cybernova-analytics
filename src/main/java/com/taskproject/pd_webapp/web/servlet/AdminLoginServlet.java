package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.AdminUserDAO;
import com.taskproject.pd_webapp.model.AdminUser;
import com.taskproject.pd_webapp.util.ValidationUtil;
import com.taskproject.pd_webapp.util.HTMLSanitizer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Admin Login Servlet
 * Handles authentication against the admin_users database table
 * Uses SHA-256 with salt for secure password verification
 * Includes input validation for username and password
 */
@WebServlet(urlPatterns = "/admin/login")
public class AdminLoginServlet extends HttpServlet {

    private final AdminUserDAO adminUserDAO = new AdminUserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession existingSession = request.getSession(false);

        if (existingSession != null &&
                Boolean.TRUE.equals(existingSession.getAttribute("adminAuthenticated"))) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        request.setAttribute("pageTitle", "Admin Login - CyberNova Analytics Ltd");
        String success = request.getParameter("success");
        if (success != null && !success.isBlank()) {
            request.setAttribute("success", success);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = ValidationUtil.safeTrim(request.getParameter("username"));
        String password = request.getParameter("password");

        // Validate inputs
        String validationError = ValidationUtil.validateAdminLogin(username, password);

        if (validationError != null) {
            request.setAttribute("pageTitle", "Admin Login - CyberNova Analytics Ltd");
            request.setAttribute("error", validationError);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
            return;
        }

        try {
            // Authenticate against database
            AdminUser adminUser = adminUserDAO.authenticateAdmin(username, password);

            if (adminUser != null) {
                // Invalidate old session
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                // Sanitize username for safe display
                String sanitizedUsername = HTMLSanitizer.sanitizeTextField(adminUser.getUsername());

                // Create new session
                HttpSession session = request.getSession(true);
                session.setAttribute("adminAuthenticated", true);
                session.setAttribute("adminUsername", sanitizedUsername);
                session.setAttribute("adminId", adminUser.getAdminId());
                session.setAttribute("adminEmail", adminUser.getEmail());

                // Session expires after 30 minutes of inactivity
                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }

            request.setAttribute("pageTitle", "Admin Login - CyberNova Analytics Ltd");
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("pageTitle", "Admin Login - CyberNova Analytics Ltd");
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
        }
    }
}