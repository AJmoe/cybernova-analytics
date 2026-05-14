<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</main>
<div class="chatbot-shell" data-chatbot-shell hidden aria-hidden="true">
    <button type="button" class="btn btn-primary chatbot-toggle" data-chatbot-toggle aria-expanded="false" aria-controls="chatbot-panel">
        <i class="bi bi-chat-dots me-1" aria-hidden="true"></i>
        Help Bot
    </button>

    <section id="chatbot-panel" class="chatbot-panel panel" role="dialog" aria-modal="false" aria-labelledby="chatbot-title" hidden>
        <div class="chatbot-panel__header">
            <div>
                <p class="chatbot-eyebrow">Virtual Assistant</p>
                <h3 id="chatbot-title">CyberNova Help Bot</h3>
            </div>
            <button type="button" class="btn btn-outline-light btn-sm chatbot-close" data-chatbot-close aria-label="Close chatbot">
                <i class="bi bi-x-lg" aria-hidden="true"></i>
            </button>
        </div>

        <div class="chatbot-panel__messages" data-chatbot-messages aria-live="polite" aria-relevant="additions"></div>

        <div class="chatbot-panel__quick-replies" aria-label="Suggested questions">
            <button type="button" class="btn btn-outline-light btn-sm" data-chatbot-prompt="testimonials">Testimonials</button>
            <button type="button" class="btn btn-outline-light btn-sm" data-chatbot-prompt="security request">Security request</button>
            <button type="button" class="btn btn-outline-light btn-sm" data-chatbot-prompt="gallery">Gallery</button>
            <button type="button" class="btn btn-outline-light btn-sm" data-chatbot-prompt="admin registration">Admin registration</button>
        </div>

        <form class="chatbot-panel__form" data-chatbot-form>
            <label class="visually-hidden" for="chatbot-input">Type your message</label>
            <input id="chatbot-input" type="text" class="chatbot-input" data-chatbot-input placeholder="Ask about requests, testimonials, gallery, or admin help" autocomplete="off">
            <button type="submit" class="btn btn-primary chatbot-send">Send</button>
        </form>
    </section>
</div>
<footer class="site-footer">
    <div class="site-footer__inner">
        <p>&copy; 2026 CyberNova Analytics Ltd. All rights reserved.</p>
        <nav aria-label="Footer navigation" class="site-footer__nav">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/solutions">Cybersecurity Solutions</a>
            <a href="${pageContext.request.contextPath}/events">Case Studies</a>
            <a href="${pageContext.request.contextPath}/articles">Cyber Blog</a>
            <a href="${pageContext.request.contextPath}/testimonials">Testimonials</a>
            <a href="${pageContext.request.contextPath}/gallery">Workshop Gallery</a>
            <a href="${pageContext.request.contextPath}/contact">Contact Security Team</a>
        </nav>
    </div>
</footer>
</body>
</html>
