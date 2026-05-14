<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jsp" %>

<%
    String testimonialSuccess = request.getParameter("testimonialSuccess");
    String testimonialError = request.getParameter("testimonialError");
    String securityError = request.getParameter("error");
    boolean showTestimonial = testimonialSuccess != null || testimonialError != null;
%>

<section class="page-heading">
    <span class="eyebrow">Contact CyberNova</span>
    <h2>Submit a request or share your experience.</h2>
    <p>
        Use this page to contact the security team or submit a testimonial for admin review.
    </p>
</section>

<section class="panel">
    <div style="display:flex; gap:1rem; flex-wrap:wrap;">
        <button type="button" class="btn btn-primary" onclick="showSecurityForm()">
            Contact Security Team
        </button>

        <button type="button" class="btn btn-outline-light" onclick="showTestimonialForm()">
            Create Testimonial
        </button>
    </div>
</section>

<section id="securityRequestSection">
    <section class="page-heading">
        <span class="eyebrow">Contact security team</span>
        <h2>Submit a technical security request.</h2>
        <p>Use this form to report a cybersecurity issue or request technical assistance.</p>
    </section>

    <% if (securityError != null) { %>
    <div class="alert alert-danger">
        <%= securityError %>
    </div>
    <% } %>

    <form class="security-form panel" method="post" action="${pageContext.request.contextPath}/submit-security-request" novalidate data-validate="true">
        <div class="form-grid">
            <div class="form-group">
                <label for="sec-name">
                    Name
                    <input type="text" id="sec-name" name="name" required pattern="^[a-zA-Z\s'-]{2,}" maxlength="100"
                           title="Name can only contain letters, spaces, hyphens, and apostrophes"
                           placeholder="Your full name" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-email">
                    Email
                    <input type="email" id="sec-email" name="email" required maxlength="255"
                           placeholder="you@example.com" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-phone">
                    Phone
                    <input type="tel" id="sec-phone" name="phone" required pattern="^[0-9\s+():-]{10,}" maxlength="20"
                           title="Please enter a valid phone number"
                           placeholder="+267 ..." inputmode="tel" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-org">
                    Organization
                    <input type="text" id="sec-org" name="organization" required maxlength="255"
                           placeholder="Company or institution" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-country">
                    Country
                    <input type="text" id="sec-country" name="country" required maxlength="100"
                           placeholder="Country" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-job">
                    Job title
                    <input type="text" id="sec-job" name="jobTitle" required maxlength="100"
                           placeholder="Your role" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="sec-issue">
                    Type of security issue
                    <select id="sec-issue" name="issueType" required data-validate="true">
                        <option value="">Select an issue type</option>
                        <option value="Phishing Response">Phishing Response</option>
                        <option value="Incident Response">Incident Response</option>
                        <option value="Vulnerability Assessment">Vulnerability Assessment</option>
                        <option value="Cloud Hardening">Cloud Hardening</option>
                        <option value="Security Awareness Training">Security Awareness Training</option>
                        <option value="Compliance Review">Compliance Review</option>
                    </select>
                </label>
            </div>

            <div class="form-group" style="grid-column: 1 / -1;">
                <label for="sec-description">
                    Description of technical problem
                    <textarea id="sec-description" name="description" rows="6" required minlength="20" maxlength="5000"
                              placeholder="Describe your security issue in detail..."
                              title="Description must be between 20 and 5000 characters" data-validate="true"></textarea>
                </label>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Submit Security Request</button>
    </form>
</section>

<section id="testimonialSection" style="display:none;">
    <section class="page-heading">
        <span class="eyebrow">Client testimonial</span>
        <h2>Share your CyberNova experience.</h2>
        <p>
            Submit a professional testimonial about the service you received. Your testimonial will be reviewed by the admin before appearing publicly.
        </p>
    </section>

    <section class="panel">
        <h3>How to write a good testimonial</h3>
        <p>
            A strong testimonial explains what service you received, what problem was solved,
            how CyberNova helped, and how satisfied you were with the support.
        </p>

        <p>
            Please do not include passwords, private system information, confidential company data,
            or offensive language.
        </p>
    </section>

    <% if (testimonialSuccess != null) { %>
    <div class="alert alert-success">
        <% if ("submitted".equals(testimonialSuccess)) { %>
        Your testimonial has been submitted for admin review.
        <% } else { %>
        Testimonial action completed successfully.
        <% } %>
    </div>
    <% } %>

    <% if (testimonialError != null) { %>
    <div class="alert alert-danger">
        <%= testimonialError %>
    </div>
    <% } %>

    <form class="security-form panel" method="post" action="${pageContext.request.contextPath}/submit-testimonial" novalidate data-validate="true">
        <div class="form-grid">

            <div class="form-group">
                <label for="test-name">
                    Name
                    <input type="text" id="test-name" name="name" placeholder="Your full name" required pattern="^[a-zA-Z\s'-]{2,}"
                           maxlength="100" title="Name can only contain letters, spaces, hyphens, and apostrophes" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-email">
                    Email
                    <input type="email" id="test-email" name="email" placeholder="you@example.com" required maxlength="255" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-phone">
                    Phone
                    <input type="tel" id="test-phone" name="phone" placeholder="+267 ..." required pattern="^[0-9\s+():-]{10,}"
                           maxlength="20" title="Please enter a valid phone number" inputmode="tel" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-org">
                    Organization
                    <input type="text" id="test-org" name="organization" placeholder="Company or institution" required maxlength="255" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-country">
                    Country
                    <input type="text" id="test-country" name="country" placeholder="Country" required maxlength="100" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-job">
                    Job Title
                    <input type="text" id="test-job" name="jobTitle" placeholder="Your role" required maxlength="100" data-validate="true">
                </label>
            </div>

            <div class="form-group">
                <label for="test-service">
                    Service Type
                    <select id="test-service" name="serviceType" required data-validate="true">
                        <option value="">Select service type</option>
                        <option value="Ransomware Detection">Ransomware Detection</option>
                        <option value="DDoS Protection">DDoS Protection</option>
                        <option value="Vulnerability Assessment">Vulnerability Assessment</option>
                        <option value="Malware Removal">Malware Removal</option>
                        <option value="Security Audit">Security Audit</option>
                        <option value="Security Awareness Training">Security Awareness Training</option>
                    </select>
                </label>
            </div>

            <div class="form-group">
                <label for="test-rating">
                    Rating
                    <select id="test-rating" name="rating" required data-validate="true">
                        <option value="">Select rating</option>
                        <option value="5">5 - Excellent</option>
                        <option value="4">4 - Very Good</option>
                        <option value="3">3 - Good</option>
                        <option value="2">2 - Fair</option>
                        <option value="1">1 - Poor</option>
                    </select>
                </label>
            </div>

            <div class="form-group">
                <label for="test-title">
                    Testimonial Title
                    <input type="text" id="test-title" name="title" placeholder="e.g. Fast and professional response" required
                           minlength="5" maxlength="200" title="Title must be between 5 and 200 characters" data-validate="true">
                </label>
            </div>

            <div class="form-group" style="grid-column: 1 / -1;">
                <label for="test-feedback">
                    Feedback
                    <textarea id="test-feedback" name="feedbackText" rows="6"
                              placeholder="Describe your experience with CyberNova..." required
                              minlength="30" maxlength="5000"
                              title="Feedback must be between 30 and 5000 characters" data-validate="true"></textarea>
                </label>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Submit Testimonial for Review</button>
    </form>
</section>

<script>
    const showTestimonialOnLoad = <%= showTestimonial %>;

    function showSecurityForm() {
        document.getElementById("securityRequestSection").style.display = "block";
        document.getElementById("testimonialSection").style.display = "none";
    }

    function showTestimonialForm() {
        document.getElementById("securityRequestSection").style.display = "none";
        document.getElementById("testimonialSection").style.display = "block";
    }

    if (showTestimonialOnLoad) {
        showTestimonialForm();
    }
</script>

<%@ include file="common/footer.jsp" %>