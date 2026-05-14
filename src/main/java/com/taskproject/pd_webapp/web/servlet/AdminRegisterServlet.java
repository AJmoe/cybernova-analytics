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

@WebServlet("/admin/register")
public class AdminRegisterServlet extends HttpServlet {

    private final AdminUserDAO adminUserDAO = new AdminUserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Register New Admin - CyberNova Analytics Ltd");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/register-admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = ValidationUtil.safeTrim(request.getParameter("username"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String fullName = ValidationUtil.safeTrim(request.getParameter("fullName"));
        String password = ValidationUtil.safeTrim(request.getParameter("password"));
        String confirmPassword = ValidationUtil.safeTrim(request.getParameter("confirmPassword"));

        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);

        // Validate all inputs
        String validationError = ValidationUtil.validateAdminRegistration(
            username, email, fullName, password, confirmPassword
        );

        if (validationError != null) {
            forwardWithError(request, response, validationError);
            return;
        }

        // Check if username already exists
        if (adminUserDAO.usernameExists(username)) {
            forwardWithError(request, response, "That username is already in use. Please choose a different one.");
            return;
        }

        // Check if email already exists
        if (adminUserDAO.emailExists(email)) {
            forwardWithError(request, response, "That email address is already registered. Please use a different one.");
            return;
        }

        try {
            // Sanitize full name to prevent XSS attacks
            String sanitizedFullName = HTMLSanitizer.sanitizeTextField(fullName);

            AdminUser adminUser = new AdminUser();
            adminUser.setUsername(username);
            adminUser.setEmail(email);
            adminUser.setFullName(sanitizedFullName);
            adminUser.setPasswordHash(AdminUserDAO.hashPassword(password));
            adminUser.setActive(true);

            adminUserDAO.createAdminUser(adminUser);

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/admin/login?success=adminRegistered");
        } catch (IllegalArgumentException e) {
            forwardWithError(request, response, e.getMessage());
        } catch (Exception e) {
            forwardWithError(request, response, "An error occurred while creating the admin account. Please try again.");
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Register New Admin - CyberNova Analytics Ltd");
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/register-admin.jsp").forward(request, response);
    }
}
