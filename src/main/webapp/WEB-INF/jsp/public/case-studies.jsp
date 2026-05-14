<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/1/2026
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.ContentCard" %>
<%@ include file="../common/header.jsp" %>

<%
    List<ContentCard> cards = (List<ContentCard>) request.getAttribute("cards");
%>

<section class="page-heading">
    <span class="eyebrow">CyberNova Case Studies</span>
    <h2>Threat Mitigation Case Studies</h2>
    <p>Explore practical examples of how CyberNova supports organizations in identifying, managing, and reducing cybersecurity risks.</p>
</section>

<section class="content-card-grid">
    <%
        if (cards != null && !cards.isEmpty()) {
            for (ContentCard card : cards) {
    %>
    <article class="panel solution-card">
        <% if (card.getImageUrl() != null && !card.getImageUrl().trim().isEmpty()) { %>
        <img src="<%= card.getImageUrl() %>" alt="<%= card.getTitle() %>" class="card-image">
        <% } else { %>
        <div class="card-image placeholder-image">📁</div>
        <% } %>

        <div class="card-body">
            <span class="eyebrow">CASE STUDY</span>
            <h3><%= card.getTitle() %></h3>
            <p><%= card.getDescription() %></p>

            <% if (card.getExternalLink() != null && !card.getExternalLink().trim().isEmpty()) { %>
            <a class="btn btn-primary" href="<%= card.getExternalLink() %>" target="_blank">Read Case Study</a>
            <% } else { %>
            <button class="btn btn-outline-light" disabled>Details Coming Soon</button>
            <% } %>
        </div>
    </article>
    <%
        }
    } else {
    %>
    <article class="panel">
        <h3>No case studies available yet</h3>
        <p>The admin has not published case study cards yet.</p>
    </article>
    <%
        }
    %>
</section>

<%@ include file="../common/footer.jsp" %>