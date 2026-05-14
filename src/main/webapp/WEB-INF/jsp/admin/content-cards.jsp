<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List,com.taskproject.pd_webapp.model.ContentCard" %>
<%@ include file="../common/header.jsp" %>

<%
    List<ContentCard> cards = (List<ContentCard>) request.getAttribute("cards");
    String selectedPageType = (String) request.getAttribute("selectedPageType");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<section class="page-heading">
    <span class="eyebrow">Admin content control</span>
    <h2>Manage Website Content Cards</h2>
    <p>Create, edit, hide, and delete cards for Cybersecurity Solutions, Case Studies, and Cyber Blog pages.</p>
</section>

<% if (success != null) { %>
<div class="alert alert-success">
    <%= success.equals("cardAdded") ? "Card added successfully." :
            success.equals("cardDeleted") ? "Card deleted successfully." :
                    success.equals("cardUpdated") ? "Card updated successfully." :
                            "Action completed successfully." %>
</div>
<% } %>

<% if (error != null) { %>
<div class="alert alert-danger">
    <%= error.equals("missingFields") ? "Please fill in all required fields." :
            error.equals("invalidPageType") ? "Invalid page type selected." :
                    error.equals("missingCardId") ? "Missing card ID." :
                            error.equals("invalidCardId") ? "Invalid card ID." :
                                    error.equals("cardNotFound") ? "Card not found." :
                                            "Something went wrong." %>
</div>
<% } %>

<section class="panel">
    <h3>Add New Card</h3>

    <form method="post" action="${pageContext.request.contextPath}/admin/content-cards/add" class="content-form" novalidate>

        <label>
            Page Type
            <select name="pageType" required>
                <option value="">-- Select Page Type --</option>
                <option value="SOLUTION">Cybersecurity Solutions</option>
                <option value="CASE_STUDY">Case Studies</option>
                <option value="BLOG">Cyber Blog</option>
            </select>
        </label>

        <label>
            Card Title
            <input type="text" name="title" placeholder="e.g. Ransomware Detection" required
                   minlength="3" maxlength="200" title="Title must be between 3 and 200 characters">
        </label>

        <label>
            Description
            <textarea name="description" rows="4" placeholder="Write a short card description..."
                      required minlength="10" maxlength="2000"
                      title="Description must be between 10 and 2000 characters"></textarea>
        </label>

        <label>
            Image URL
            <input type="url" name="imageUrl" placeholder="https://example.com/image.jpg"
                   title="Must be a valid URL starting with http:// or https://">
            <small>Optional. Must be a valid URL.</small>
        </label>

        <label>
            External Link
            <input type="url" name="externalLink" placeholder="https://example.com/article"
                   title="Must be a valid URL starting with http:// or https://">
            <small>Optional. Must be a valid URL.</small>
        </label>

        <label>
            Status
            <select name="status">
                <option value="PUBLISHED">Published</option>
                <option value="HIDDEN">Hidden</option>
            </select>
        </label>

        <button type="submit" class="btn btn-primary">Add Card</button>
    </form>
</section>

<section class="panel">
    <h3>Filter Cards</h3>

    <form method="get" action="${pageContext.request.contextPath}/admin/content-cards" class="filter-bar">
        <label>
            Page Type
            <select name="pageType">
                <option value="" <%= selectedPageType == null || selectedPageType.isEmpty() ? "selected" : "" %>>All</option>
                <option value="SOLUTION" <%= "SOLUTION".equals(selectedPageType) ? "selected" : "" %>>Cybersecurity Solutions</option>
                <option value="CASE_STUDY" <%= "CASE_STUDY".equals(selectedPageType) ? "selected" : "" %>>Case Studies</option>
                <option value="BLOG" <%= "BLOG".equals(selectedPageType) ? "selected" : "" %>>Cyber Blog</option>
            </select>
        </label>

        <button type="submit" class="btn btn-primary">Apply Filter</button>
    </form>
</section>

<section class="panel">
    <h3>Existing Cards</h3>

    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>Title</th>
                <th>Page Type</th>
                <th>Status</th>
                <th>Image</th>
                <th>External Link</th>
                <th>Created</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <%
                if (cards != null && !cards.isEmpty()) {
                    for (ContentCard card : cards) {
            %>
            <tr>
                <td><%= card.getTitle() %></td>
                <td><%= card.getPageType() %></td>
                <td><%= card.getStatus() %></td>

                <td>
                    <% if (card.getImageUrl() != null && !card.getImageUrl().isEmpty()) { %>
                    <img src="<%= card.getImageUrl() %>"
                         alt="Card image"
                         style="width:70px;height:45px;object-fit:cover;border-radius:8px;">
                    <% } else { %>
                    No image
                    <% } %>
                </td>

                <td>
                    <% if (card.getExternalLink() != null && !card.getExternalLink().isEmpty()) { %>
                    <a href="<%= card.getExternalLink() %>"
                       target="_blank"
                       class="btn btn-sm btn-outline-light">
                        Open
                    </a>
                    <% } else { %>
                    No link
                    <% } %>
                </td>

                <td><%= card.getCreatedAt() %></td>

                <td>
                    <div style="display:flex; gap:0.5rem; flex-wrap:wrap;">
                        <a class="btn btn-sm btn-primary"
                           href="${pageContext.request.contextPath}/admin/content-cards/edit?cardId=<%= card.getCardId() %>">
                            Edit
                        </a>

                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/content-cards/delete"
                              onsubmit="return confirm('Are you sure you want to delete this card?');">
                            <input type="hidden" name="cardId" value="<%= card.getCardId() %>">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="text-center">No content cards found.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</section>

<%@ include file="../common/footer.jsp" %>