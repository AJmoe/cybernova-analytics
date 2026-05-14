package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.ContentCardDAO;
import com.taskproject.pd_webapp.model.ContentCard;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/content-cards/edit")
public class EditContentCardServlet extends HttpServlet {

    private final ContentCardDAO contentCardDAO = new ContentCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cardIdParam = request.getParameter("cardId");

        if (cardIdParam == null || cardIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=missingCardId");
            return;
        }

        try {
            int cardId = Integer.parseInt(cardIdParam);

            ContentCard card = contentCardDAO.findById(cardId);

            if (card == null) {
                response.sendRedirect(request.getContextPath()
                        + "/admin/content-cards?error=cardNotFound");
                return;
            }

            request.setAttribute("pageTitle", "Edit Content Card - CyberNova");
            request.setAttribute("card", card);

            request.getRequestDispatcher("/WEB-INF/jsp/admin/edit-content-card.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=invalidCardId");
        }
    }
}