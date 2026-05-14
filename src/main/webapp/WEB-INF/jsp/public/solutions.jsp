<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.ContentCard" %>
<%@ include file="../common/header.jsp" %>

<%
    List<ContentCard> cards = (List<ContentCard>) request.getAttribute("cards");
%>

<section class="page-heading">
    <span class="eyebrow">CyberNova Solutions</span>
    <h2>Cybersecurity Solutions</h2>
    <p>
        Explore CyberNova’s AI-driven cybersecurity services designed to help organizations detect,
        prevent, and respond to modern digital threats.
    </p>
</section>

<section class="content-card-grid">
    <%
        if (cards != null && !cards.isEmpty()) {
            for (ContentCard card : cards) {
    %>
    <article class="panel solution-card">
        <%
            String imageUrl = card.getImageUrl();
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
        %>
        <img src="<%= imageUrl %>"
             alt="<%= card.getTitle() %>"
             class="card-image">
        <%
        } else {
        %>
        <div class="card-image placeholder-image">
            🛡️
        </div>
        <%
            }
        %>

        <div class="card-body">
            <span class="eyebrow"><%= card.getPageType() %></span>
            <h3><%= card.getTitle() %></h3>
            <p><%= card.getDescription() %></p>

            <%
                String externalLink = card.getExternalLink();
                if (externalLink != null && !externalLink.trim().isEmpty()) {
            %>
            <a class="btn btn-primary"
               href="<%= externalLink %>"
               target="_blank">
                Explore Solution
            </a>
            <%
            } else {
            %>
            <button class="btn btn-outline-light" disabled>
                More Details Coming Soon
            </button>
            <%
                }
            %>
        </div>
    </article>
    <%
        }
    } else {
    %>
    <article class="panel">
        <h3>No solutions available yet</h3>
        <p>
            The admin has not published cybersecurity solution cards yet.
        </p>
    </article>
    <%
        }
    %>
</section>

<%@ include file="../common/footer.jsp" %>