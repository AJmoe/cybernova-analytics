package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.TestimonialDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/testimonials/delete")
public class DeleteTestimonialServlet extends HttpServlet {

    private final TestimonialDAO testimonialDAO = new TestimonialDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String testimonialIdParam = request.getParameter("testimonialId");

        if (testimonialIdParam == null || testimonialIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/testimonials?error=missingTestimonialId");
            return;
        }

        try {
            int testimonialId = Integer.parseInt(testimonialIdParam);
            testimonialDAO.deleteTestimonial(testimonialId);
            response.sendRedirect(request.getContextPath()
                    + "/admin/testimonials?success=testimonialDeleted");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/testimonials?error=invalidTestimonialId");
        }
    }
}

