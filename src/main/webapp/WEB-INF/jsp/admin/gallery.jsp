<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.WorkshopEvent,com.taskproject.pd_webapp.dao.GalleryDAO.GalleryImageRow" %>
<%@ include file="../common/header.jsp" %>

<%
    List<WorkshopEvent> events = (List<WorkshopEvent>) request.getAttribute("events");
    List<GalleryImageRow> galleryImages = (List<GalleryImageRow>) request.getAttribute("galleryImages");
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<div class="page-heading">
    <span class="eyebrow">Gallery Management</span>
    <h2>Workshop Events and Gallery</h2>
</div>

<% if (success != null) { %>
<div class="alert alert-success">
    <% if ("eventCreated".equals(success)) { %>
    Workshop event added successfully.
    <% } else if ("eventRemoved".equals(success)) { %>
    Workshop event removed from active list.
    <% } else if ("imageRemoved".equals(success)) { %>
    Gallery image removed successfully.
    <% } else { %>
    Action completed successfully.
    <% } %>
</div>
<% } %>

<% if (error != null) { %>
<div class="alert alert-danger">
    <% if ("missingFields".equals(error)) { %>
    Please fill in title, description and event date.
    <% } else if ("invalidDate".equals(error)) { %>
    Please use a valid date/time.
    <% } else if ("invalidNumber".equals(error)) { %>
    Invalid numeric value provided.
    <% } else { %>
    Request failed. Please try again.
    <% } %>
</div>
<% } %>

<section class="security-form panel">
    <h3>Add Workshop Event</h3>
    <p>Create an active workshop event so it appears in the image upload dropdown and public gallery.</p>

    <form method="POST" action="${pageContext.request.contextPath}/admin/gallery" class="form-grid" novalidate>
        <input type="hidden" name="action" value="createEvent">

        <div>
            <label for="title">Event Title</label>
            <input type="text" id="title" name="title" required minlength="3" maxlength="255"
                   placeholder="e.g., Security Incident Response Training"
                   title="Title must be between 3 and 255 characters">
        </div>

        <div>
            <label for="eventDate">Event Date and Time</label>
            <input type="datetime-local" id="eventDate" name="eventDate" required>
            <small>Select a future date and time for the workshop.</small>
        </div>

        <div class="full-width">
            <label for="description">Description</label>
            <textarea id="description" name="description" rows="3" required
                      minlength="10" maxlength="2000"
                      placeholder="Describe the workshop content and objectives..."
                      title="Description must be between 10 and 2000 characters"></textarea>
        </div>

        <div>
            <label for="location">Location</label>
            <input type="text" id="location" name="location" maxlength="200"
                   placeholder="e.g., Gaborone / Online">
            <small>Optional.</small>
        </div>

        <div>
            <label for="capacity">Capacity</label>
            <input type="number" id="capacity" name="capacity" min="0" placeholder="Optional"
                   title="Capacity must be a non-negative number">
            <small>Optional. Maximum attendees.</small>
        </div>

        <div>
            <label for="instructor">Instructor</label>
            <input type="text" id="instructor" name="instructor" maxlength="200"
                   placeholder="Instructor name"
                   pattern="^[a-zA-Z\s'-]*$"
                   title="Instructor name can only contain letters, spaces, hyphens, and apostrophes">
            <small>Optional.</small>
        </div>

        <div>
            <label for="eventType">Event Type</label>
            <input type="text" id="eventType" name="eventType" maxlength="200"
                   placeholder="e.g., Incident Response Training">
            <small>Optional. Category or type of workshop.</small>
        </div>

        <div class="full-width">
            <button type="submit" class="btn btn-primary">Save Event</button>
            <a href="${pageContext.request.contextPath}/admin/gallery/add-images" class="btn btn-outline-light">Go to Add Images</a>
        </div>
    </form>
</section>

<section class="panel" style="margin-top: 2rem;">
    <h3>Active Workshop Events</h3>
    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Date</th>
                <th>Location</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (events != null && !events.isEmpty()) {
                    for (WorkshopEvent ev : events) {
            %>
            <tr>
                <td><%= ev.getEventId() %></td>
                <td><%= ev.getTitle() %></td>
                <td><%= ev.getEventDate() == null ? "N/A" : ev.getEventDate().toString().replace('T', ' ') %></td>
                <td><%= ev.getLocation() == null ? "" : ev.getLocation() %></td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/admin/gallery" style="display:inline;"
                          onsubmit="return confirm('Remove this event from active list?');">
                        <input type="hidden" name="action" value="deactivateEvent">
                        <input type="hidden" name="eventId" value="<%= ev.getEventId() %>">
                        <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" class="text-center">No active events yet.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</section>

<section class="panel" style="margin-top: 2rem;">
    <h3>Gallery Images</h3>
    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>ID</th>
                <th>Event</th>
                <th>Image Title</th>
                <th>Image URL</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (galleryImages != null && !galleryImages.isEmpty()) {
                    for (GalleryImageRow image : galleryImages) {
            %>
            <tr>
                <td><%= image.getGalleryId() %></td>
                <td><%= image.getEventTitle() %></td>
                <td><%= image.getImageTitle() == null ? "Image" : image.getImageTitle() %></td>
                <td><a href="<%= image.getImageUrl() %>" target="_blank"><%= image.getImageUrl() %></a></td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/admin/gallery" style="display:inline;"
                          onsubmit="return confirm('Delete this gallery image?');">
                        <input type="hidden" name="action" value="deleteImage">
                        <input type="hidden" name="galleryId" value="<%= image.getGalleryId() %>">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" class="text-center">No gallery images found.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</section>

<%@ include file="../common/footer.jsp" %>

