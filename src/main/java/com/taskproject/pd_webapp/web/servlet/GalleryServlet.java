package com.taskproject.pd_webapp.web.servlet;

import com.taskproject.pd_webapp.dao.GalleryDAO;
import com.taskproject.pd_webapp.model.WorkshopGallery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/gallery")
public class GalleryServlet extends HttpServlet {
    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WorkshopGallery> events = galleryDAO.findAllEventsWithImages();
        request.setAttribute("events", events);
        request.setAttribute("pageTitle", "Workshop Gallery - CyberNova Analytics Ltd");
        request.getRequestDispatcher("/WEB-INF/jsp/gallery.jsp").forward(request, response);
    }
}
