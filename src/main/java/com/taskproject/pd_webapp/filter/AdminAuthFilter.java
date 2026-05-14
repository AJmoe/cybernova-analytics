package com.taskproject.pd_webapp.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // No setup required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI();

        boolean isLoginRequest =
                uri.equals(contextPath + "/admin/login") ||
                        uri.equals(contextPath + "/admin/login.jsp");

        boolean isStaticResource =
                uri.contains("/css/") ||
                        uri.contains("/js/") ||
                        uri.contains("/images/") ||
                        uri.contains("/assets/") ||
                        uri.endsWith(".css") ||
                        uri.endsWith(".js") ||
                        uri.endsWith(".png") ||
                        uri.endsWith(".jpg") ||
                        uri.endsWith(".jpeg") ||
                        uri.endsWith(".svg") ||
                        uri.endsWith(".ico");

        if (isLoginRequest || isStaticResource) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        boolean authenticated =
                session != null &&
                        Boolean.TRUE.equals(session.getAttribute("adminAuthenticated"));

        if (!authenticated) {
            httpResponse.sendRedirect(contextPath + "/admin/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}