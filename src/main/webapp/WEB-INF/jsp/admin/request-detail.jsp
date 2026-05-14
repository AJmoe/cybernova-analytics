<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,com.taskproject.pd_webapp.model.SecurityRequest" %>
<%@ include file="../common/header.jsp" %>
<%
    SecurityRequest securityRequest = (SecurityRequest) request.getAttribute("securityRequest");
    @SuppressWarnings("unchecked")
    List<String> statusOptions = (List<String>) request.getAttribute("statusOptions");
    boolean updated = "1".equals(request.getParameter("updated"));
%>

<section class="page-heading">
    <span class="eyebrow">Security request detail</span>
    <h2>Request #<%= securityRequest != null ? securityRequest.getRequestId() : "N/A" %></h2>
    <p>Review the submitted request and update its processing status.</p>
</section>

<% if (updated) { %>
    <div class="panel" style="border-left: 4px solid var(--success);">Status updated successfully.</div>
<% } %>

<% if (securityRequest != null) { %>
<section class="section-grid">
    <article class="panel">
        <h3>Request details</h3>
        <p><strong>Name:</strong> <%= securityRequest.getClientName() %></p>
        <p><strong>Email:</strong> <%= securityRequest.getClientEmail() %></p>
        <p><strong>Phone:</strong> <%= securityRequest.getClientPhone() %></p>
        <p><strong>Organization:</strong> <%= securityRequest.getOrganization() %></p>
        <p><strong>Country:</strong> <%= securityRequest.getCountry() %></p>
        <p><strong>Job title:</strong> <%= securityRequest.getJobTitle() %></p>
        <p><strong>Issue type:</strong> <%= securityRequest.getTypeOfSecurityIssue() %></p>
        <p><strong>Submitted:</strong> <%= securityRequest.getCreatedAt() %></p>
    </article>

    <article class="panel">
        <h3>Description</h3>
        <p><%= securityRequest.getDescription() %></p>
    </article>
</section>

<section class="panel">
    <h3>Update status</h3>
    <% if (statusOptions == null || statusOptions.isEmpty()) { %>
        <div class="panel" style="border-left: 4px solid var(--danger);">Oops, no choices received from the server. Please try again.</div>
        <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/admin/dashboard">Back to dashboard</a>
    <% } else { %>
    <form method="post" action="${pageContext.request.contextPath}/admin/request">
        <input type="hidden" name="id" value="<%= securityRequest.getRequestId() %>">
        <label>
            Current status
            <select name="status">
                <%
                    for (String option : statusOptions) {
                %>
                    <option value="<%= option %>" <%= option.equals(securityRequest.getStatus()) ? "selected" : "" %>><%= option %></option>
                <%
                    }
                %>
            </select>
        </label>
        <button class="btn btn-primary" type="submit">Save status</button>
        <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/admin/dashboard">Back to dashboard</a>
    </form>
    <% } %>
</section>
<% } %>

<%@ include file="../common/footer.jsp" %>

