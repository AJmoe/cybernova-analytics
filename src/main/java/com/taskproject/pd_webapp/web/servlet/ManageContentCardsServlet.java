package com.taskproject.pd_webapp.web.servlet;


import com.taskproject.pd_webapp.dao.ContentCardDAO;
import com.taskproject.pd_webapp.model.ContentCard;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/content-cards")
public class ManageContentCardsServlet extends HttpServlet {

    private final ContentCardDAO contentCardDAO = new ContentCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageType = request.getParameter("pageType");

        List<ContentCard> cards;

        if (pageType != null && !pageType.trim().isEmpty()) {
            cards = contentCardDAO.findByPageType(pageType);
        } else {
            cards = contentCardDAO.findAll();
        }

        request.setAttribute("pageTitle", "Manage Content Cards - CyberNova");
        request.setAttribute("cards", cards);
        request.setAttribute("selectedPageType", pageType == null ? "" : pageType);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/content-cards.jsp")
                .forward(request, response);
    }
}