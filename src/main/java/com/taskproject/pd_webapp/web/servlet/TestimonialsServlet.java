package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.TestimonialDAO;
import com.taskproject.pd_webapp.model.Testimonial;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/testimonials")
public class TestimonialsServlet extends HttpServlet {

    private final TestimonialDAO testimonialDAO = new TestimonialDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Testimonial> testimonials = testimonialDAO.findLatestPublished(6);

        request.setAttribute("pageTitle", "Client Testimonials - CyberNova");
        request.setAttribute("testimonials", testimonials);

        request.getRequestDispatcher("/WEB-INF/jsp/testimonials.jsp")
                .forward(request, response);
    }
}