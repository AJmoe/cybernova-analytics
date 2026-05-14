<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.WorkshopEvent" %>
<%@ include file="../common/header.jsp" %>

<%
    List<WorkshopEvent> events = (List<WorkshopEvent>) request.getAttribute("events");
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<div class="page-heading">
    <span class="eyebrow">Gallery Management</span>
    <h2>Add Images to Workshop Events</h2>
</div>

<% if (success != null) { %>
<div class="alert alert-success">
    Image added successfully! You can add more images or close this page.
</div>
<% } %>

<% if (error != null) { %>
<div class="alert alert-danger">
    <% if ("missingFields".equals(error)) { %>
        Please fill in the Event and Image URL fields.
    <% } else if ("invalidFormat".equals(error)) { %>
        Please enter valid numbers for Event ID and Display Order.
    <% } else { %>
        An error occurred. Please try again.
    <% } %>
</div>
<% } %>

<section class="security-form panel">
    <h3>Add Image to Event</h3>
    <p>Select a workshop event and add one or more image URLs. You can add multiple images per event.</p>

    <% if (events == null || events.isEmpty()) { %>
    <div class="alert alert-danger">
        No active workshop events found. Create an event first in
        <a href="${pageContext.request.contextPath}/admin/gallery">Manage Gallery</a>, then return here to add images.
    </div>
    <% } %>

    <form method="POST" action="${pageContext.request.contextPath}/admin/gallery/add-images" class="form-grid" novalidate>
        <div>
            <label for="eventId">Workshop Event</label>
            <select id="eventId" name="eventId" required>
                <option value="">-- Select an event --</option>
                <%
                    if (events != null && !events.isEmpty()) {
                        for (WorkshopEvent ev : events) {
                %>
                <option value="<%= ev.getEventId() %>"><%= ev.getTitle() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="full-width">
            <label for="imageUrl">Image URL</label>
            <input type="url" id="imageUrl" name="imageUrl" placeholder="https://example.com/image.jpg" required
                   title="Must be a valid URL starting with http:// or https://">
            <small>Enter the full URL to the image file (https:// recommended).</small>
        </div>

        <div>
            <label for="imageTitle">Image Title (optional)</label>
            <input type="text" id="imageTitle" name="imageTitle" placeholder="e.g., Workshop Introduction"
                   maxlength="255"
                   title="Title can contain letters, numbers, spaces, and common punctuation">
            <small>A short title for the image (used for alt text).</small>
        </div>

        <div>
            <label for="displayOrder">Display Order</label>
            <input type="number" id="displayOrder" name="displayOrder" value="0" min="0" max="999">
            <small>Lower numbers appear first (0 = default order).</small>
        </div>

        <div class="full-width">
            <button type="submit" class="btn btn-primary" <%= (events == null || events.isEmpty()) ? "disabled" : "" %>>Add Image</button>
            <a href="${pageContext.request.contextPath}/admin" class="btn btn-outline-light">Back to Dashboard</a>
        </div>
    </form>

    <hr style="margin: 2rem 0; border: none; border-top: 1px solid rgba(15, 44, 102, 0.12);">

    <h4>Notes</h4>
    <ul>
        <li>Paste the complete URL of the image file (must be accessible online).</li>
        <li>You can add multiple images to the same event by submitting this form multiple times.</li>
        <li>Images will appear in the Workshop Gallery page, grouped by event.</li>
        <li>If an event has no images, it will still appear with its description in the gallery.</li>
    </ul>
</section>

<%@ include file="../common/footer.jsp" %>

