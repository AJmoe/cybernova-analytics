package com.taskproject.pd_webapp.web.servlet;


import com.taskproject.pd_webapp.dao.ContentCardDAO;
import com.taskproject.pd_webapp.model.ContentCard;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/cyber-blog")
public class BlogServlet extends HttpServlet {

    private final ContentCardDAO dao = new ContentCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ContentCard> cards = dao.findLatestPublishedByPageType("BLOG", 6);

        request.setAttribute("cards", cards);
        request.setAttribute("pageTitle", "Cyber Blog - CyberNova");

        request.getRequestDispatcher("/WEB-INF/jsp/public/blog.jsp")
                .forward(request, response);
    }
}