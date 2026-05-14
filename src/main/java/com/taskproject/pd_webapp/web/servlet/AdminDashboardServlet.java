package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.model.RequestAnalytics;
import com.taskproject.pd_webapp.model.SecurityRequest;
import com.taskproject.pd_webapp.model.RegionalDemand;
import com.taskproject.pd_webapp.service.RequestAnalyticsService;
import com.taskproject.pd_webapp.service.SecurityRequestRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin", "/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    private final SecurityRequestRepository repository = SecurityRequestRepository.getInstance();
    private final RequestAnalyticsService analyticsService = new RequestAnalyticsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String issueType = request.getParameter("issueType");
        String country = request.getParameter("country");
        String search = request.getParameter("search");

        List<SecurityRequest> allRequests = repository.findAll();
        List<SecurityRequest> filteredRequests = repository.findFiltered(issueType, country, search);
        RequestAnalytics analytics = analyticsService.buildAnalytics(allRequests);
        List<RegionalDemand> regionalDemand = repository.getRegionalDemand(8);

        request.setAttribute("pageTitle", "Admin Dashboard - CyberNova Analytics Ltd");
        request.setAttribute("requests", filteredRequests);
        request.setAttribute("analytics", analytics);
        request.setAttribute("regionalDemand", regionalDemand);
        request.setAttribute("issueTypes", repository.getDistinctIssueTypes());
        request.setAttribute("countries", repository.getDistinctCountries());
        request.setAttribute("selectedIssueType", issueType == null ? "" : issueType);
        request.setAttribute("selectedCountry", country == null ? "" : country);
        request.setAttribute("searchTerm", search == null ? "" : search);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
    }
}
