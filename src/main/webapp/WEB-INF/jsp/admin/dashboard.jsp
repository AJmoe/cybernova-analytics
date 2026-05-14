<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,com.taskproject.pd_webapp.model.SecurityRequest,com.taskproject.pd_webapp.model.RequestAnalytics,com.taskproject.pd_webapp.model.RegionalDemand" %>
<%@ include file="../common/header.jsp" %>
<%
    RequestAnalytics analytics = (RequestAnalytics) request.getAttribute("analytics");
    List<SecurityRequest> requests = (List<SecurityRequest>) request.getAttribute("requests");
    List<String> issueTypes = (List<String>) request.getAttribute("issueTypes");
    List<String> countries = (List<String>) request.getAttribute("countries");
    List<RegionalDemand> regionalDemand = (List<RegionalDemand>) request.getAttribute("regionalDemand");
    String selectedIssueType = (String) request.getAttribute("selectedIssueType");
    String selectedCountry = (String) request.getAttribute("selectedCountry");
    String searchTerm = (String) request.getAttribute("searchTerm");
%>

<section class="page-heading">
    <span class="eyebrow">Admin analytics</span>
    <h2>Security request dashboard</h2>
    <p>Filter submitted requests and review regional demand for CyberNova services.</p>
</section>

<section class="analytics-grid">
    <article class="panel analytics-card">
        <h3>Total requests</h3>
        <strong><%= analytics != null ? analytics.getTotalRequests() : 0 %></strong>
    </article>
    <article class="panel analytics-card">
        <h3>Top issue type</h3>
        <strong><%= analytics != null ? analytics.getTopIssueType() : "N/A" %></strong>
    </article>
    <article class="panel analytics-card">
        <h3>Top region</h3>
        <strong><%= analytics != null ? analytics.getTopCountry() : "N/A" %></strong>
    </article>
</section>

<section class="panel">
    <h3>Admin Management Tools</h3>
    <p>Manage the dynamic content shown on public CyberNova pages.</p>

    <div class="analytics-grid">

        <article class="panel analytics-card">
            <h3>Content Cards</h3>
            <p>Manage Cybersecurity Solutions, Case Studies, and Cyber Blog cards.</p>
            <a class="btn btn-primary"
               href="${pageContext.request.contextPath}/admin/content-cards">
                Manage Content Cards
            </a>
        </article>

        <article class="panel analytics-card">
            <h3>Testimonials</h3>
            <p>Review submitted testimonials and approve them for public display.</p>
            <a class="btn btn-outline-light"
               href="${pageContext.request.contextPath}/admin/testimonials">
                Manage Testimonials
            </a>
        </article>

        <article class="panel analytics-card">
            <h3>Gallery / Events</h3>
            <p>Add or remove workshop gallery and cybersecurity event items.</p>
            <a class="btn btn-outline-light"
               href="${pageContext.request.contextPath}/admin/gallery">
                Manage Gallery
            </a>
        </article>

        <article class="panel analytics-card">
            <h3>Add Gallery Images</h3>
            <p>Upload image URLs to workshop events. Add multiple images per event.</p>
            <a class="btn btn-primary"
               href="${pageContext.request.contextPath}/admin/gallery/add-images">
                Add Images
            </a>
        </article>

        <article class="panel analytics-card">
            <h3>Register New Admin</h3>
            <p>Create a new administrator account for the CyberNova dashboard.</p>
            <a class="btn btn-outline-light"
               href="${pageContext.request.contextPath}/admin/register">
                Register Admin
            </a>
        </article>

    </div>
</section>
<form class="panel filter-bar" method="get" action="${pageContext.request.contextPath}/admin/dashboard">
    <label>
        Issue type
        <select name="issueType">
            <option value="">All</option>
            <%
                if (issueTypes != null) {
                    for (String option : issueTypes) {
            %>
                <option value="<%= option %>" <%= option.equals(selectedIssueType) ? "selected" : "" %>><%= option %></option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <label>
        Country
        <input list="countryOptions" type="text" name="country" value="<%= selectedCountry == null ? "" : selectedCountry %>" placeholder="e.g. Botswana">
        <datalist id="countryOptions">
            <%
                if (countries != null) {
                    for (String option : countries) {
            %>
                <option value="<%= option %>"></option>
            <%
                    }
                }
            %>
        </datalist>
    </label>
    <label>
        Search
        <input type="text" name="search" value="<%= searchTerm == null ? "" : searchTerm %>" placeholder="Search name, org, or job title">
    </label>
    <button class="btn btn-primary" type="submit">Apply filters</button>
</form>

<section class="panel">
    <h3>Submitted security requests</h3>
    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>Name</th>
                <th>Organization</th>
                <th>Country</th>
                <th>Issue type</th>
                <th>Status</th>
                <th>Submitted</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (requests != null && !requests.isEmpty()) {
                    for (SecurityRequest requestItem : requests) {
            %>
                <tr>
                    <td><%= requestItem.getClientName() %></td>
                    <td><%= requestItem.getOrganization() %></td>
                    <td><%= requestItem.getCountry() %></td>
                    <td><%= requestItem.getTypeOfSecurityIssue() %></td>
                    <td><%= requestItem.getStatus() %></td>
                    <td><%= requestItem.getCreatedAt() %></td>
                    <td><a class="btn btn-sm btn-outline-light" href="${pageContext.request.contextPath}/admin/request?id=<%= requestItem.getRequestId() %>">View details</a></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="7" class="text-center">No requests match the current filters.</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</section>

<section class="panel">
    <h3>Regional demand snapshot</h3>
    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle mb-0">
            <thead>
            <tr>
                <th>Region</th>
                <th>Country</th>
                <th>Service type</th>
                <th>Requests</th>
                <th>Demand level</th>
                <th>Last updated</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (regionalDemand != null && !regionalDemand.isEmpty()) {
                    for (RegionalDemand demand : regionalDemand) {
            %>
                <tr>
                    <td><%= demand.getRegion() %></td>
                    <td><%= demand.getCountry() %></td>
                    <td><%= demand.getServiceType() %></td>
                    <td><%= demand.getRequestCount() %></td>
                    <td><%= demand.getDemandLevel() %></td>
                    <td><%= demand.getLastUpdated() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="6" class="text-center">No regional demand data available.</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</section>

<%@ include file="../common/footer.jsp" %>
