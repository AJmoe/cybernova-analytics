<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jsp" %>

<section class="page-heading">
    <span class="eyebrow">Workshop gallery</span>
    <h2>Training sessions, technical demos, and team awareness events.</h2>
    <p>Photos and highlights from CyberNova’s workshops and cybersecurity events.</p>
</section>

<section class="content-card-grid">
    <%-- If admin provides a list of gallery events via request attribute "events", render them dynamically --%>
    <%@ page import="java.util.List,com.taskproject.pd_webapp.model.WorkshopGallery" %>
    <%
        List<WorkshopGallery> events = (List<WorkshopGallery>) request.getAttribute("events");
    %>

    <%
        if (events != null && !events.isEmpty()) {
            for (WorkshopGallery ev : events) {
    %>
    <article class="panel">
        <h3><%= ev.getTitle() %></h3>
        <p><%= ev.getDescription() %></p>
        <% String imgs = ev.getImageUrls();
           if (imgs != null && !imgs.trim().isEmpty()) {
               String[] parts = imgs.split(",");
        %>
        <div class="gallery-thumbs">
            <% for (String u : parts) { u = u.trim(); if (!u.isEmpty()) { %>
            <img src="<%= u %>" alt="<%= ev.getTitle() %>" style="width:100%;max-width:220px;margin-right:0.5rem;border-radius:8px;"/>
            <% } } %>
        </div>
        <% } %>
    </article>
    <%
            }
        } else {
    %>
    <!-- Fallback static content when no events provided -->
    <article class="panel">
        <h3>Phishing Awareness Session</h3>
        <p>Interactive staff briefing with simulated email triage and reporting exercises.</p>
    </article>
    <article class="panel">
        <h3>Security Operations Demo</h3>
        <p>Live walkthrough of alert triage, monitoring, and incident escalation flows.</p>
    </article>
    <article class="panel">
        <h3>Technical Resilience Workshop</h3>
        <p>Focus on backups, access control, and response planning for critical systems.</p>
    </article>
    <%
        }
    %>
</section>

<%@ include file="common/footer.jsp" %>
