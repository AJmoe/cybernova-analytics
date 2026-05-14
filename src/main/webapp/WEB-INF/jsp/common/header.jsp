<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String assetVersion = "20260512a"; %>
<% String pageTitle = (String) request.getAttribute("pageTitle"); %>
<% String currentUri = request.getRequestURI(); %>
<% String contextPath = request.getContextPath(); %>
<%
    String route = currentUri.startsWith(contextPath) ? currentUri.substring(contextPath.length()) : currentUri;
    route = route == null || route.isBlank() ? "/" : route;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="context-path" content="${pageContext.request.contextPath}">
    <title><%= (pageTitle != null && !pageTitle.isBlank()) ? pageTitle : "CyberNova Analytics Ltd" %></title>
    <!-- Bootstrap CSS from WebJars -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap-icons/1.11.3/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css?v=<%= assetVersion %>">
    <!-- Form Validation CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form-validation.css?v=<%= assetVersion %>">
    <!-- Main JavaScript -->
    <script defer src="${pageContext.request.contextPath}/assets/js/main.js?v=<%= assetVersion %>"></script>
    <!-- Form Validation JavaScript -->
    <script defer src="${pageContext.request.contextPath}/assets/js/form-validation.js?v=<%= assetVersion %>"></script>
</head>
<body>
<a class="skip-link" href="#main-content">Skip to content</a>
<header class="site-header">
    <div class="site-header__brand">
        <h1>CyberNova Analytics Ltd</h1>
        <p>AI-driven cybersecurity monitoring and digital resilience</p>
    </div>
    <button type="button" class="site-header__menu-toggle" aria-label="Toggle navigation" aria-expanded="false" data-mobile-menu-toggle>
        <span class="menu-icon">
            <span class="menu-bar"></span>
            <span class="menu-bar"></span>
            <span class="menu-bar"></span>
        </span>
    </button>
    <nav aria-label="Primary navigation" class="site-header__nav" data-mobile-menu aria-expanded="false">
        <a class="<%= ("/".equals(route) || route.startsWith("/home")) ? "is-active" : "" %>" href="${pageContext.request.contextPath}/home">Home</a>
        <a class="<%= route.startsWith("/solutions") || route.startsWith("/solution") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/solutions">Cybersecurity Solutions</a>
        <!-- Updated: point navigation to dynamic content routes handled by servlets that fetch published cards -->
        <a class="<%= route.startsWith("/case-studies") || route.startsWith("/case-study") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/case-studies">Case Studies</a>
        <a class="<%= route.startsWith("/cyber-blog") || route.startsWith("/blog") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/cyber-blog">Cyber Blog</a>
        <a class="<%= route.startsWith("/testimonials") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/testimonials">Testimonials</a>
        <a class="<%= route.startsWith("/gallery") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/gallery">Workshop Gallery</a>
        <a class="<%= route.startsWith("/contact") || route.startsWith("/inquiry") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/contact">Contact Security Team</a>
        <a class="<%= route.startsWith("/admin") ? "is-active" : "" %>" href="${pageContext.request.contextPath}/admin/login">Admin</a>
    </nav>
</header>
<main id="main-content">
