document.addEventListener('DOMContentLoaded', () => {
    initializeMobileMenu();
    document.querySelectorAll('.site-header__nav a').forEach((link) => {
        if (link.href === window.location.href) {
            link.classList.add('is-active');
        }
    });

    document.querySelectorAll('.panel').forEach((panel) => {
        panel.addEventListener('mouseenter', () => panel.classList.add('shadow-lg'));
        panel.addEventListener('mouseleave', () => panel.classList.remove('shadow-lg'));
    });

    initializeChatbot();
});

function initializeMobileMenu() {
    const toggle = document.querySelector('[data-mobile-menu-toggle]');
    const menu = document.querySelector('[data-mobile-menu]');

    if (!toggle || !menu) {
        return;
    }

    /**
     * Update hamburger button visibility based on screen size
     * Show on mobile (≤768px), hide on desktop (>768px)
     */
    const updateMenuVisibility = () => {
        const isMobile = window.innerWidth <= 768;
        
        if (isMobile) {
            // Mobile: Show hamburger, reset menu state
            toggle.style.display = 'block';
        } else {
            // Desktop: Hide hamburger, show menu
            toggle.style.display = 'none';
            menu.setAttribute('aria-expanded', 'false');
        }
    };

    // Initial setup
    updateMenuVisibility();
    
    // Update on window resize
    window.addEventListener('resize', updateMenuVisibility);

    /**
     * Toggle menu open/close on button click
     */
    toggle.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();
        
        const isExpanded = menu.getAttribute('aria-expanded') === 'true';
        const newState = !isExpanded;
        
        toggle.setAttribute('aria-expanded', newState);
        menu.setAttribute('aria-expanded', newState);
    });

    /**
     * Close menu when a navigation link is clicked
     */
    menu.querySelectorAll('a').forEach((link) => {
        link.addEventListener('click', () => {
            toggle.setAttribute('aria-expanded', 'false');
            menu.setAttribute('aria-expanded', 'false');
        });
    });

    /**
     * Close menu when clicking outside of header
     */
    document.addEventListener('click', (e) => {
        const isMenuOpen = menu.getAttribute('aria-expanded') === 'true';
        const isClickOnHeader = toggle.contains(e.target) || menu.contains(e.target);
        
        if (isMenuOpen && !isClickOnHeader) {
            toggle.setAttribute('aria-expanded', 'false');
            menu.setAttribute('aria-expanded', 'false');
        }
    });
}

function initializeChatbot() {
    const shell = document.querySelector('[data-chatbot-shell]');
    if (!shell) {
        return;
    }

    const contextPath = document.querySelector('meta[name="context-path"]')?.content || '';
    const currentPath = window.location.pathname || '';
    const adminPrefix = `${contextPath}/admin`;

    if (currentPath === `${contextPath}/admin` || currentPath.startsWith(adminPrefix)) {
        return;
    }

    const toggleButton = shell.querySelector('[data-chatbot-toggle]');
    const closeButton = shell.querySelector('[data-chatbot-close]');
    const panel = shell.querySelector('#chatbot-panel');
    const messages = shell.querySelector('[data-chatbot-messages]');
    const form = shell.querySelector('[data-chatbot-form]');
    const input = shell.querySelector('[data-chatbot-input]');

    if (!toggleButton || !closeButton || !panel || !messages || !form || !input) {
        return;
    }

    const botGreeting = 'Hello! I can help with security requests, testimonials, gallery events, and admin access.';

    const responseRules = [
        {
            keywords: ['hello', 'hi', 'hey', 'good morning', 'good afternoon', 'good evening'],
            response: 'Hello! How can I help today? Try asking about testimonials, security requests, gallery events, or admin registration.'
        },
        {
            keywords: ['testimonial', 'testimonials', 'review', 'feedback'],
            response: 'Testimonials are submitted on the Contact page using the “Create Testimonial” form. Admin reviews them before they appear publicly.'
        },
        {
            keywords: ['security request', 'request', 'contact', 'issue', 'incident', 'support'],
            response: 'Use the Contact Security Team form to submit a request. Include your issue type, description, and contact details so the team can respond quickly.'
        },
        {
            keywords: ['gallery', 'event', 'events', 'workshop'],
            response: 'The Workshop Gallery shows event images. Admins can create events and add image URLs from the Gallery Management page.'
        },
        {
            keywords: ['admin', 'login', 'register', 'registration', 'new admin'],
            response: 'Admins can log in from the Admin page. After logging in, an admin can register a new admin from the dashboard if that feature is enabled.'
        },
        {
            keywords: ['solution', 'solutions', 'services', 'cybersecurity', 'blog', 'case study', 'case studies'],
            response: 'You can explore CyberNova’s cybersecurity solutions, case studies, and cyber blog from the main navigation bar.'
        },
        {
            keywords: ['thanks', 'thank you', 'bye', 'goodbye'],
            response: 'You’re welcome! If you need anything else, just ask.'
        }
    ];

    const state = {
        isOpen: false,
        welcomeShown: false
    };

    const openChat = () => {
        shell.hidden = false;
        shell.setAttribute('aria-hidden', 'false');
        panel.hidden = false;
        toggleButton.setAttribute('aria-expanded', 'true');
        state.isOpen = true;

        if (!state.welcomeShown) {
            appendMessage('bot', botGreeting);
            state.welcomeShown = true;
        }

        input.focus();
    };

    const closeChat = () => {
        panel.hidden = true;
        shell.hidden = false;
        shell.setAttribute('aria-hidden', 'false');
        toggleButton.setAttribute('aria-expanded', 'false');
        state.isOpen = false;
    };

    const appendMessage = (sender, text) => {
        const message = document.createElement('div');
        message.className = `chatbot-message chatbot-message--${sender}`;
        message.textContent = text;
        messages.appendChild(message);
        messages.scrollTop = messages.scrollHeight;
    };

    const pickReply = (rawText) => {
        const normalized = rawText.toLowerCase().trim();
        if (!normalized) {
            return 'Please type a question or keyword so I can help.';
        }

        const matchedRule = responseRules.find((rule) =>
            rule.keywords.some((keyword) => normalized.includes(keyword))
        );

        if (matchedRule) {
            return matchedRule.response;
        }

        return 'I can help with testimonials, security requests, gallery events, admin login, and solutions. Try one of those keywords.';
    };

    const submitMessage = (text) => {
        const cleaned = text.trim();
        if (!cleaned) {
            return;
        }

        appendMessage('user', cleaned);
        const reply = pickReply(cleaned);

        window.setTimeout(() => {
            appendMessage('bot', reply);
        }, 250);
    };

    toggleButton.addEventListener('click', () => {
        if (state.isOpen) {
            closeChat();
        } else {
            openChat();
        }
    });

    closeButton.addEventListener('click', closeChat);

    shell.querySelectorAll('[data-chatbot-prompt]').forEach((button) => {
        button.addEventListener('click', () => {
            const prompt = button.getAttribute('data-chatbot-prompt') || button.textContent || '';
            if (!state.isOpen) {
                openChat();
            }
            submitMessage(prompt);
        });
    });

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const value = input.value;
        submitMessage(value);
        input.value = '';
        input.focus();
        if (!state.isOpen) {
            openChat();
        }
    });

    // Start hidden; show only when the user opens it on public pages.
    shell.hidden = false;
    closeChat();
}

