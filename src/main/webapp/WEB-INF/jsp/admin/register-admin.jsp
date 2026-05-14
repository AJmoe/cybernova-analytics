<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/header.jsp" %>

<%
    String error = (String) request.getAttribute("error");
    String username = (String) request.getAttribute("username");
    String email = (String) request.getAttribute("email");
    String fullName = (String) request.getAttribute("fullName");
%>

<section class="page-heading">
    <span class="eyebrow">Admin management</span>
    <h2>Register a new administrator</h2>
    <p>This page is available only to logged-in admins.</p>
</section>

<section class="security-form panel">
    <h3>Create Admin Account</h3>

    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/admin/register" class="form-grid" novalidate>
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username" value="<%= username == null ? "" : username %>"
                   required minlength="3" maxlength="20" pattern="^[a-zA-Z0-9_-]{3,20}$"
                   title="Username must be 3-20 characters (letters, numbers, hyphens, underscores only)"
                   placeholder="3-20 characters">
        </div>

        <div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="<%= email == null ? "" : email %>"
                   required maxlength="255"
                   placeholder="admin@example.com">
        </div>

        <div class="full-width">
            <label for="fullName">Full name</label>
            <input type="text" id="fullName" name="fullName" value="<%= fullName == null ? "" : fullName %>"
                   required pattern="^[a-zA-Z\s'-]{2,}" maxlength="255"
                   title="Full name can only contain letters, spaces, hyphens, and apostrophes"
                   placeholder="Full name">
        </div>

        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required minlength="8"
                   placeholder="Minimum 8 characters"
                   title="Password must be at least 8 characters long">
            <small>Minimum 8 characters.</small>
        </div>

        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8"
                   placeholder="Confirm password">
        </div>

        <div class="full-width">
            <button type="submit" class="btn btn-primary">Register Admin</button>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-light">Back to Dashboard</a>
        </div>
    </form>
</section>

<%@ include file="../common/footer.jsp" %>
