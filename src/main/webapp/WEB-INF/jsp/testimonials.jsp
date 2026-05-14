<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.Testimonial" %>
<%@ include file="common/header.jsp" %>

<%
    List<Testimonial> testimonials =
            (List<Testimonial>) request.getAttribute("testimonials");
%>

<section class="page-heading">
    <span class="eyebrow">Client feedback</span>
    <h2>Trusted by security-focused organisations.</h2>
    <p>
        Approved client testimonials showing how CyberNova supports digital resilience,
        cybersecurity response, and risk reduction.
    </p>
</section>

<section class="testimonial-grid">
    <%
        if (testimonials != null && !testimonials.isEmpty()) {
            for (Testimonial testimonial : testimonials) {
    %>

    <article class="testimonial-card">
        <div class="testimonial-top">
            <span class="testimonial-service"><%= testimonial.getServiceType() %></span>

            <div class="testimonial-stars">
                <%
                    for (int i = 1; i <= 5; i++) {
                        if (i <= testimonial.getRating()) {
                %>
                <span class="star-filled">★</span>
                <%
                } else {
                %>
                <span class="star-empty">☆</span>
                <%
                        }
                    }
                %>
            </div>
        </div>

        <h3><%= testimonial.getTitle() %></h3>

        <p class="testimonial-quote">
            “<%= testimonial.getFeedbackText() %>”
        </p>

        <div class="testimonial-footer">
            <span>Approved Client Feedback</span>
            <small><%= testimonial.getPublishedDate() %></small>
        </div>
    </article>

    <%
        }
    } else {
    %>

    <article class="panel">
        <h3>No approved testimonials yet</h3>
        <p>Client testimonials will appear here after admin approval.</p>

        <a class="btn btn-primary"
           href="${pageContext.request.contextPath}/contact">
            Submit Testimonial
        </a>
    </article>

    <%
        }
    %>
</section>

<section class="panel testimonial-cta">
    <h3>Want to share your experience?</h3>
    <p>
        Submit a testimonial through the contact page. It will be reviewed by the admin before publication.
    </p>

    <a class="btn btn-primary"
       href="${pageContext.request.contextPath}/contact">
        Create Testimonial
    </a>
</section>

<%@ include file="common/footer.jsp" %>