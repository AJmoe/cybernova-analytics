package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.TestimonialDAO;
import com.taskproject.pd_webapp.model.Testimonial;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/testimonials")
public class AdminManageTestimonialsServlet extends HttpServlet {

    private final TestimonialDAO testimonialDAO = new TestimonialDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filter = request.getParameter("filter");

        List<Testimonial> testimonials;

        if ("pending".equalsIgnoreCase(filter)) {
            testimonials = testimonialDAO.findPending();
        } else {
            testimonials = testimonialDAO.findAll();
        }

        request.setAttribute("pageTitle", "Manage Testimonials - CyberNova");
        request.setAttribute("testimonials", testimonials);
        request.setAttribute("selectedFilter", filter == null ? "all" : filter);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/testimonials.jsp")
                .forward(request, response);
    }
}
