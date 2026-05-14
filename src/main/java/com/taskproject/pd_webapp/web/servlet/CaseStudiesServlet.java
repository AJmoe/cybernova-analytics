package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.ContentCardDAO;
import com.taskproject.pd_webapp.model.ContentCard;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/case-studies")
public class CaseStudiesServlet extends HttpServlet {

    private final ContentCardDAO dao = new ContentCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ContentCard> cards = dao.findLatestPublishedByPageType("CASE_STUDY", 6);

        request.setAttribute("cards", cards);
        request.setAttribute("pageTitle", "Case Studies - CyberNova");

        request.getRequestDispatcher("/WEB-INF/jsp/public/case-studies.jsp")
                .forward(request, response);
    }
}