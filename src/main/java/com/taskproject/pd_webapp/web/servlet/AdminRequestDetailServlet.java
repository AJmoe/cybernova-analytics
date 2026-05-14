package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.model.SecurityRequest;
import com.taskproject.pd_webapp.service.SecurityRequestRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/request")
public class AdminRequestDetailServlet extends HttpServlet {
    private final SecurityRequestRepository repository = SecurityRequestRepository.getInstance();
    private static final List<String> STATUS_OPTIONS = List.of("NEW", "IN_PROGRESS", "RESOLVED", "CLOSED");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = parseId(request.getParameter("id"));
        if (id <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing request id");
            return;
        }

        SecurityRequest securityRequest = repository.findById(id);
        if (securityRequest == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Security request not found");
            return;
        }

        request.setAttribute("pageTitle", "Security Request #" + id + " - CyberNova Analytics Ltd");
        request.setAttribute("securityRequest", securityRequest);
        request.setAttribute("statusOptions", STATUS_OPTIONS);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/request-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = parseId(request.getParameter("id"));
        if (id <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing request id");
            return;
        }

        String status = request.getParameter("status");
        if (status == null || status.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing status");
            return;
        }

        repository.updateStatus(id, status);
        response.sendRedirect(request.getContextPath() + "/admin/request?id=" + id + "&updated=1");
    }

    private long parseId(String idValue) {
        try {
            return idValue == null ? -1 : Long.parseLong(idValue);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}

