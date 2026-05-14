<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jsp" %>

<section class="hero-panel">
    <div>
        <span class="eyebrow">🚀 AI-Powered Cybersecurity</span>
        <h2>Protect Your Digital Future with Advanced Threat Detection</h2>
        <p>CyberNova Analytics Ltd delivers enterprise-grade cybersecurity solutions with AI-driven monitoring, rapid incident response, and comprehensive resilience planning. Trust us to safeguard your digital operations.</p>
        <div class="hero-actions">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/contact">Get Started Today</a>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/solutions">View Solutions</a>
        </div>
    </div>
</section>

<!-- Stats Section -->
<section class="section-grid" style="margin: 3rem 0; text-align: center;">
    <article class="panel">
        <div style="font-size: 2.5rem; font-weight: 800; color: #2b5fbf; margin-bottom: 0.5rem;">500+</div>
        <h3 style="margin: 0; color: #0a1f44;">Organizations Protected</h3>
        <p style="margin-bottom: 0;">Across Africa & beyond</p>
    </article>
    <article class="panel">
        <div style="font-size: 2.5rem; font-weight: 800; color: #f2a900; margin-bottom: 0.5rem;">99.9%</div>
        <h3 style="margin: 0; color: #0a1f44;">Threat Detection</h3>
        <p style="margin-bottom: 0;">Accuracy Rate</p>
    </article>
    <article class="panel">
        <div style="font-size: 2.5rem; font-weight: 800; color: #d32f2f; margin-bottom: 0.5rem;">24/7</div>
        <h3 style="margin: 0; color: #0a1f44;">Expert Support</h3>
        <p style="margin-bottom: 0;">Round-the-clock monitoring</p>
    </article>
</section>

<!-- Services Grid -->
<section class="section-grid">
    <article class="panel">
        <div style="font-size: 2.5rem; margin-bottom: 1rem;">🛡️</div>
        <h3>Cybersecurity Solutions</h3>
        <p>Comprehensive risk assessments, real-time monitoring, system hardening, incident response, and business continuity planning tailored to your needs.</p>
        <a href="${pageContext.request.contextPath}/solutions" style="color: #2b5fbf; font-weight: 600; text-decoration: none;">Learn More →</a>
    </article>
    <article class="panel">
        <div style="font-size: 2.5rem; margin-bottom: 1rem;">📊</div>
        <h3>Case Studies</h3>
        <p>Discover how CyberNova helped leading organizations reduce security risks and recover faster from threats with our proven methodologies.</p>
        <a href="${pageContext.request.contextPath}/case-studies" style="color: #2b5fbf; font-weight: 600; text-decoration: none;">Explore Cases →</a>
    </article>
    <article class="panel">
        <div style="font-size: 2.5rem; margin-bottom: 1rem;">📚</div>
        <h3>Cyber Security Blog</h3>
        <p>Stay informed with expert insights on phishing threats, cloud security, compliance regulations, and the latest cybersecurity trends shaping the industry.</p>
        <a href="${pageContext.request.contextPath}/cyber-blog" style="color: #2b5fbf; font-weight: 600; text-decoration: none;">Read Articles →</a>
    </article>
</section>

<!-- Testimonials Section -->
<section class="testimonial-panel">
    <h3>⭐ What Our Clients Say</h3>
    <div class="testimonial-grid">
        <article class="testimonial-card">
            <p class="testimonial-quote">"CyberNova's rapid threat detection identified critical vulnerabilities we didn't know existed. Their team was professional and direct in their recommendations."</p>
            <div class="testimonial-footer">
                <strong>Sarah Johnson</strong>
                <span class="testimonial-service">Financial Services Director</span>
            </div>
        </article>
        <article class="testimonial-card">
            <p class="testimonial-quote">"The incident response was seamless. They helped us contain a breach within hours and provided clear guidance on recovery steps."</p>
            <div class="testimonial-footer">
                <strong>Thabo Mthembu</strong>
                <span class="testimonial-service">IT Manager</span>
            </div>
        </article>
        <article class="testimonial-card">
            <p class="testimonial-quote">"Their security awareness training transformed how our team thinks about cyber threats. Highly recommended for any organization."</p>
            <div class="testimonial-footer">
                <strong>Margaret Chen</strong>
                <span class="testimonial-service">HR & Compliance Officer</span>
            </div>
        </article>
    </div>
</section>

<!-- CTA Section -->
<section class="hero-panel" style="margin-top: 3rem;">
    <div>
        <h2 style="background: linear-gradient(135deg, #0a1f44 0%, #2b5fbf 100%); font-size: 2rem;">Ready to Strengthen Your Security?</h2>
        <p>Schedule a free security consultation with our expert team today.</p>
        <div class="hero-actions" style="margin-top: 2rem;">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/contact">Schedule Consultation</a>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/gallery">View Gallery</a>
        </div>
    </div>
</section>

<%@ include file="common/footer.jsp" %>
