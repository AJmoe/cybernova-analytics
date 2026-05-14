package com.taskproject.pd_webapp.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/article")
public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Article Details - CyberNova Analytics Ltd");
        request.getRequestDispatcher("/WEB-INF/jsp/article-detail.jsp").forward(request, response);
    }
}
