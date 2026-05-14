package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.ContentCardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/content-cards/delete")
public class DeleteContentCardServlet extends HttpServlet {

    private final ContentCardDAO contentCardDAO = new ContentCardDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cardIdParam = request.getParameter("cardId");

        if (cardIdParam == null || cardIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=missingCardId");
            return;
        }

        try {
            int cardId = Integer.parseInt(cardIdParam);
            contentCardDAO.deleteCard(cardId);

            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?success=cardDeleted");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/content-cards?error=invalidCardId");
        }
    }
}