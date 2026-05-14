package com.taskproject.pd_webapp.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/articles")
public class ArticlesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Cyber Blog - CyberNova Analytics Ltd");
        request.getRequestDispatcher("/WEB-INF/jsp/articles.jsp").forward(request, response);
    }
}
