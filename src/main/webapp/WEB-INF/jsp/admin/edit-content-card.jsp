<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.taskproject.pd_webapp.model.ContentCard" %>
<%@ include file="../common/header.jsp" %>

<%
    ContentCard card = (ContentCard) request.getAttribute("card");
%>

<section class="page-heading">
    <span class="eyebrow">Admin content control</span>
    <h2>Edit Content Card</h2>
    <p>Update the selected website card content.</p>
</section>

<% if (card != null) { %>

<section class="panel">
    <form method="post"
          action="${pageContext.request.contextPath}/admin/content-cards/update"
          class="content-form" novalidate>

        <input type="hidden" name="cardId" value="<%= card.getCardId() %>">

        <label>
            Page Type
            <select name="pageType" required>
                <option value="SOLUTION" <%= "SOLUTION".equals(card.getPageType()) ? "selected" : "" %>>
                    Cybersecurity Solutions
                </option>
                <option value="CASE_STUDY" <%= "CASE_STUDY".equals(card.getPageType()) ? "selected" : "" %>>
                    Case Studies
                </option>
                <option value="BLOG" <%= "BLOG".equals(card.getPageType()) ? "selected" : "" %>>
                    Cyber Blog
                </option>
            </select>
        </label>

        <label>
            Card Title
            <input type="text" name="title" value="<%= card.getTitle() %>" required
                   minlength="3" maxlength="200" title="Title must be between 3 and 200 characters">
        </label>

        <label>
            Description
            <textarea name="description" rows="5" required
                      minlength="10" maxlength="2000"
                      title="Description must be between 10 and 2000 characters"><%= card.getDescription() %></textarea>
        </label>

        <label>
            Image URL
            <input type="url" name="imageUrl"
                   value="<%= card.getImageUrl() == null ? "" : card.getImageUrl() %>"
                   title="Must be a valid URL starting with http:// or https://">
            <small>Optional. Must be a valid URL.</small>
        </label>

        <label>
            External Link
            <input type="url" name="externalLink"
                   value="<%= card.getExternalLink() == null ? "" : card.getExternalLink() %>"
                   title="Must be a valid URL starting with http:// or https://">
            <small>Optional. Must be a valid URL.</small>
        </label>

        <label>
            Status
            <select name="status">
                <option value="PUBLISHED" <%= "PUBLISHED".equals(card.getStatus()) ? "selected" : "" %>>
                    Published
                </option>
                <option value="HIDDEN" <%= "HIDDEN".equals(card.getStatus()) ? "selected" : "" %>>
                    Hidden
                </option>
            </select>
        </label>

        <div style="display:flex; gap:1rem; flex-wrap:wrap;">
            <button type="submit" class="btn btn-primary">Update Card</button>

            <a href="${pageContext.request.contextPath}/admin/content-cards"
               class="btn btn-outline-light">
                Cancel
            </a>
        </div>
    </form>
</section>

<% } else { %>

<section class="panel">
    <h3>Card not found</h3>
    <p>The selected card could not be loaded.</p>
    <a href="${pageContext.request.contextPath}/admin/content-cards"
       class="btn btn-primary">
        Back to Content Cards
    </a>
</section>

<% } %>

<%@ include file="../common/footer.jsp" %>