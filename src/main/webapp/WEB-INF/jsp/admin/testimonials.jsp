<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.Testimonial" %>
<%@ include file="../common/header.jsp" %>

<%
    List<Testimonial> testimonials = (List<Testimonial>) request.getAttribute("testimonials");
    String selectedFilter = (String) request.getAttribute("selectedFilter");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<section class="page-heading">
    <span class="eyebrow">Admin testimonial approval</span>
    <h2>Manage Client Testimonials</h2>
    <p>Review submitted testimonials and approve only appropriate feedback for public display.</p>
</section>

<% if (success != null) { %>
<div class="alert alert-success">
    <%= success.equals("testimonialApproved") ? "Testimonial approved successfully." :
            success.equals("testimonialHidden") ? "Testimonial hidden successfully." :
                    success.equals("testimonialDeleted") ? "Testimonial deleted successfully." :
                            "Action completed successfully." %>
</div>
<% } %>

<% if (error != null) { %>
<div class="alert alert-danger">
    <%= error.equals("missingTestimonialId") ? "Missing testimonial ID." :
            error.equals("invalidTestimonialId") ? "Invalid testimonial ID." :
                    "Something went wrong." %>
</div>
<% } %>

<section class="panel">
    <h3>Filter Testimonials</h3>

    <form method="get" action="${pageContext.request.contextPath}/admin/testimonials" class="filter-bar">
        <label>
            Status
            <select name="filter">
                <option value="all" <%= "all".equals(selectedFilter) ? "selected" : "" %>>All</option>
                <option value="pending" <%= "pending".equals(selectedFilter) ? "selected" : "" %>>Pending Approval</option>
            </select>
        </label>

        <button type="submit" class="btn btn-primary">Apply Filter</button>
    </form>
</section>

<section class="panel">
    <h3>Submitted Testimonials</h3>

    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>Title</th>
                <th>Service</th>
                <th>Rating</th>
                <th>Feedback</th>
                <th>Status</th>
                <th>Submitted</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <%
                if (testimonials != null && !testimonials.isEmpty()) {
                    for (Testimonial testimonial : testimonials) {
            %>
            <tr>
                <td><%= testimonial.getTitle() %></td>
                <td><%= testimonial.getServiceType() %></td>
                <td>
                    <%
                        for (int i = 1; i <= testimonial.getRating(); i++) {
                    %>
                    ⭐
                    <%
                        }
                    %>
                </td>
                <td><%= testimonial.getFeedbackText() %></td>
                <td>
                    <%= testimonial.isPublished() ? "PUBLISHED" : "PENDING / HIDDEN" %>
                </td>
                <td><%= testimonial.getPublishedDate() %></td>

                <td>
                    <div style="display:flex; gap:0.5rem; flex-wrap:wrap;">

                        <% if (!testimonial.isPublished()) { %>
                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/testimonials/approve">
                            <input type="hidden" name="testimonialId"
                                   value="<%= testimonial.getTestimonialId() %>">
                            <button type="submit" class="btn btn-sm btn-primary">
                                Approve
                            </button>
                        </form>
                        <% } %>

                        <% if (testimonial.isPublished()) { %>
                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/testimonials/hide">
                            <input type="hidden" name="testimonialId"
                                   value="<%= testimonial.getTestimonialId() %>">
                            <button type="submit" class="btn btn-sm btn-outline-light">
                                Hide
                            </button>
                        </form>
                        <% } %>

                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="text-center">No testimonials found.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</section>

<%@ include file="../common/footer.jsp" %>