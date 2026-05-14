package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.TestimonialDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/testimonials/hide")
public class HideTestimonialServlet extends HttpServlet {

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

            testimonialDAO.hideTestimonial(testimonialId);

            response.sendRedirect(request.getContextPath()
                    + "/admin/testimonials?success=testimonialHidden");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/testimonials?error=invalidTestimonialId");
        }
    }
}