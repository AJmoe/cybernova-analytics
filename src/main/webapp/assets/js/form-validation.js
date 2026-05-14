/**
 * Live Form Validation & Format Correction
 * Real-time validation feedback with format examples
 * Triggers on blur (when user leaves the field)
 */

// Format examples shown to users
const FORMAT_EXAMPLES = {
    name: "e.g., John O'Brien or Mary-Jane Smith",
    email: "e.g., john@example.com",
    phone: "e.g., +267 123 4567 or (555) 123-4567",
    username: "e.g., admin-user or john_doe2",
    password: "e.g., SecurePass123! (min 8 characters)",
    title: "e.g., Fast and professional response",
    feedback: "e.g., CyberNova's team was professional...",
    description: "e.g., We experienced a phishing attack...",
    imageUrl: "e.g., https://example.com/image.jpg",
    externalLink: "e.g., https://example.com/article",
    eventTitle: "e.g., Security Incident Response Training",
    eventDate: "e.g., 2026-05-20T14:30",
    capacity: "e.g., 50",
    instructor: "e.g., John Smith",
    organizationName: "e.g., Acme Corporation Ltd",
    country: "e.g., Botswana or United States"
};

// Validation rules for each field type
const VALIDATION_RULES = {
    name: {
        pattern: /^[a-zA-Z\s'-]{2,}$/,
        errorMsg: "Invalid name format",
        hint: "Letters, spaces, hyphens (-), and apostrophes (') only"
    },
    email: {
        pattern: /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
        errorMsg: "Invalid email format",
        hint: "Must include @ and domain (e.g., user@example.com)"
    },
    phone: {
        pattern: /^[0-9\s+():-]{10,}$/,
        errorMsg: "Invalid phone format",
        hint: "At least 10 digits, can include +, -, (), and spaces"
    },
    username: {
        pattern: /^[a-zA-Z0-9_-]{3,20}$/,
        errorMsg: "Invalid username format",
        hint: "3-20 characters: letters, numbers, hyphens (-), underscores (_) only"
    },
    password: {
        minLength: 8,
        errorMsg: "Password too short",
        hint: "Minimum 8 characters"
    },
    title: {
        minLength: 5,
        maxLength: 200,
        errorMsg: "Title length incorrect",
        hint: "Between 5 and 200 characters"
    },
    feedback: {
        minLength: 30,
        maxLength: 5000,
        errorMsg: "Feedback length incorrect",
        hint: "Between 30 and 5000 characters"
    },
    description: {
        minLength: 20,
        maxLength: 5000,
        errorMsg: "Description length incorrect",
        hint: "Between 20 and 5000 characters"
    },
    url: {
        pattern: /^https?:\/\/.+/,
        errorMsg: "Invalid URL format",
        hint: "Must start with http:// or https://"
    },
    eventDate: {
        errorMsg: "Invalid date/time",
        hint: "Select a future date and time"
    },
    capacity: {
        pattern: /^\d+$/,
        errorMsg: "Must be a number",
        hint: "Enter a positive number"
    }
};

/**
 * Validate a field and show live feedback
 */
function validateFieldLive(field) {
    const fieldName = field.name || field.id;
    const fieldType = field.getAttribute('data-validation-type') || guessFieldType(fieldName, field.type);
    const value = field.value.trim();
    const container = getErrorContainer(field);
    
    // Get validation rules for this field
    const rules = getValidationRules(fieldName, fieldType, field);
    
    // Skip validation if field is empty and not required
    if (!value && !field.hasAttribute('required')) {
        clearFieldError(field, container);
        return true;
    }
    
    // Validate and get result
    let isValid = true;
    let errorMsg = '';
    
    if (rules.pattern) {
        isValid = rules.pattern.test(value);
        if (!isValid) errorMsg = rules.errorMsg;
    }
    
    if (isValid && rules.minLength && value.length < rules.minLength) {
        isValid = false;
        errorMsg = `Minimum ${rules.minLength} characters required`;
    }
    
    if (isValid && rules.maxLength && value.length > rules.maxLength) {
        isValid = false;
        errorMsg = `Maximum ${rules.maxLength} characters allowed`;
    }
    
    // Special validation for specific field types
    if (field.type === 'email' && value && !isValidEmail(value)) {
        isValid = false;
        errorMsg = rules.errorMsg;
    }
    
    if (field.type === 'url' && value && !isValidUrl(value)) {
        isValid = false;
        errorMsg = rules.errorMsg;
    }
    
    if (field.type === 'datetime-local' && value && !isValidDateTime(value)) {
        isValid = false;
        errorMsg = rules.errorMsg;
    }
    
    // Show/hide error message
    if (!isValid && value) {
        showFieldError(field, container, errorMsg, rules.hint, fieldName);
        field.classList.add('input-error');
    } else {
        clearFieldError(field, container);
        field.classList.remove('input-error');
    }
    
    return isValid;
}

/**
 * Check if email is valid
 */
function isValidEmail(email) {
    const pattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    return pattern.test(email);
}

/**
 * Check if URL is valid
 */
function isValidUrl(url) {
    try {
        new URL(url);
        return url.startsWith('http://') || url.startsWith('https://');
    } catch {
        return false;
    }
}

/**
 * Check if datetime is valid
 */
function isValidDateTime(dateStr) {
    try {
        const date = new Date(dateStr);
        return date instanceof Date && !isNaN(date);
    } catch {
        return false;
    }
}

/**
 * Get validation rules for a field
 */
function getValidationRules(fieldName, fieldType, field) {
    // Check for data attributes
    const hasPattern = field.hasAttribute('pattern');
    const hasMinLength = field.hasAttribute('minlength');
    const hasMaxLength = field.hasAttribute('maxlength');
    
    if (hasPattern) {
        return {
            pattern: new RegExp(field.getAttribute('pattern')),
            errorMsg: field.getAttribute('title') || `Invalid ${fieldName} format`,
            hint: field.getAttribute('data-hint') || VALIDATION_RULES[fieldType]?.hint || 'Invalid format'
        };
    }
    
    if (hasMinLength || hasMaxLength) {
        return {
            minLength: parseInt(field.getAttribute('minlength') || 0),
            maxLength: parseInt(field.getAttribute('maxlength') || 999999),
            errorMsg: `${fieldName} must be between ${field.getAttribute('minlength')} and ${field.getAttribute('maxlength')} characters`,
            hint: field.getAttribute('data-hint') || ''
        };
    }
    
    return VALIDATION_RULES[fieldType] || {};
}

/**
 * Guess field type from name or HTML type
 */
function guessFieldType(fieldName, htmlType) {
    fieldName = fieldName.toLowerCase();
    if (htmlType === 'email') return 'email';
    if (htmlType === 'url') return 'url';
    if (htmlType === 'tel') return 'phone';
    if (htmlType === 'datetime-local') return 'eventDate';
    if (htmlType === 'number') return 'capacity';
    
    if (fieldName.includes('name')) return 'name';
    if (fieldName.includes('email')) return 'email';
    if (fieldName.includes('phone') || fieldName.includes('tel')) return 'phone';
    if (fieldName.includes('username')) return 'username';
    if (fieldName.includes('password')) return 'password';
    if (fieldName.includes('title')) return 'title';
    if (fieldName.includes('feedback') || fieldName.includes('description')) return 'feedback';
    if (fieldName.includes('url') || fieldName.includes('link')) return 'url';
    
    return 'text';
}

/**
 * Get or create error container for a field
 */
function getErrorContainer(field) {
    const fieldId = field.id || field.name;
    let container = document.getElementById(`error-${fieldId}`);
    
    if (!container) {
        container = document.createElement('div');
        container.id = `error-${fieldId}`;
        container.className = 'field-error-container';
        field.parentNode.insertBefore(container, field.nextSibling);
    }
    
    return container;
}

/**
 * Show error message with format example
 */
function showFieldError(field, container, errorMsg, hint, fieldName) {
    const example = FORMAT_EXAMPLES[fieldName] || FORMAT_EXAMPLES[guessFieldType(fieldName, field.type)];
    
    // Remove success state if present
    container.classList.remove('field-success');
    container.classList.add('field-error');
    
    // Build error message HTML
    let errorHTML = `
        <div class="error-message">
            <strong>⚠️ ${errorMsg}</strong>
    `;
    
    if (hint) {
        errorHTML += `<div class="error-hint">${hint}</div>`;
    }
    
    if (example) {
        errorHTML += `<div class="format-example">📋 Example: <code>${example}</code></div>`;
    }
    
    errorHTML += `</div>`;
    
    container.innerHTML = errorHTML;
    
    // Show sample in background
    if (example && field.type !== 'textarea') {
        setFieldSample(field, example);
    }
}

/**
 * Clear error message
 */
function clearFieldError(field, container) {
    container.classList.remove('field-error');
    container.classList.add('field-success');
    container.innerHTML = '<div class="success-message">✓</div>';
}

/**
 * Set field placeholder/sample showing correct format
 */
function setFieldSample(field, exampleText) {
    // Extract just the sample part (after "e.g.,")
    const sample = exampleText.replace(/e\.g\.,\s*/, '').trim();
    
    // Set as placeholder for visual reference
    const originalPlaceholder = field.getAttribute('data-original-placeholder') || field.placeholder;
    if (!field.getAttribute('data-original-placeholder')) {
        field.setAttribute('data-original-placeholder', originalPlaceholder);
    }
    
    // Show sample as a lighter placeholder
    field.setAttribute('placeholder', `Format: ${sample}`);
}

/**
 * Initialize live validation for all form fields
 */
function initializeLiveFormValidation() {
    // Find all input and textarea fields
    const inputs = document.querySelectorAll('input[required], textarea[required], input[data-validate="true"], textarea[data-validate="true"]');
    
    inputs.forEach(input => {
        // Validate on blur (when leaving field)
        input.addEventListener('blur', function() {
            validateFieldLive(this);
        });
        
        // Validate on change
        input.addEventListener('change', function() {
            validateFieldLive(this);
        });
        
        // Live validation on input (every keystroke)
        input.addEventListener('input', function() {
            // Debounce: validate after user stops typing for 300ms
            clearTimeout(this.validationTimeout);
            this.validationTimeout = setTimeout(() => {
                validateFieldLive(this);
            }, 300);
        });
        
        // Restore original placeholder on focus
        input.addEventListener('focus', function() {
            const originalPlaceholder = this.getAttribute('data-original-placeholder');
            if (originalPlaceholder) {
                this.placeholder = originalPlaceholder;
            }
        });
    });
    
    console.log('✓ Live form validation initialized for', inputs.length, 'fields');
}

/**
 * Validate entire form before submission
 */
function validateFormBeforeSubmit(form) {
    const inputs = form.querySelectorAll('input[required], textarea[required], input[data-validate="true"], textarea[data-validate="true"]');
    let isFormValid = true;
    let firstInvalidField = null;
    
    inputs.forEach(input => {
        const isValid = validateFieldLive(input);
        if (!isValid) {
            isFormValid = false;
            if (!firstInvalidField) {
                firstInvalidField = input;
            }
        }
    });
    
    // Scroll to first invalid field
    if (!isFormValid && firstInvalidField) {
        firstInvalidField.scrollIntoView({ behavior: 'smooth', block: 'center' });
        firstInvalidField.focus();
        return false;
    }
    
    return isFormValid;
}

/**
 * Initialize form submission validation
 */
function initializeFormSubmitValidation() {
    const forms = document.querySelectorAll('form[data-validate="true"], form.security-form, form.content-form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // Only prevent default if form has novalidate (custom validation)
            if (this.hasAttribute('novalidate')) {
                if (!validateFormBeforeSubmit(this)) {
                    e.preventDefault();
                    return false;
                }
            }
        });
    });
    
    console.log('✓ Form submission validation initialized for', forms.length, 'forms');
}

/**
 * Add visual feedback to password confirmation matching
 */
function initializePasswordMatching() {
    const passwordField = document.querySelector('input[name="password"]');
    const confirmField = document.querySelector('input[name="confirmPassword"]');
    
    if (passwordField && confirmField) {
        const checkPasswordMatch = () => {
            const container = getErrorContainer(confirmField);
            
            if (passwordField.value && confirmField.value) {
                if (passwordField.value === confirmField.value) {
                    confirmField.classList.remove('input-error');
                    clearFieldError(confirmField, container);
                } else {
                    confirmField.classList.add('input-error');
                    showFieldError(
                        confirmField, 
                        container, 
                        "Passwords don't match",
                        "Password must match confirmation",
                        'password'
                    );
                }
            }
        };
        
        passwordField.addEventListener('change', checkPasswordMatch);
        passwordField.addEventListener('input', checkPasswordMatch);
        confirmField.addEventListener('input', checkPasswordMatch);
        confirmField.addEventListener('blur', checkPasswordMatch);
        
        console.log('✓ Password matching validation initialized');
    }
}

/**
 * Add visual feedback to select dropdowns
 */
function initializeSelectValidation() {
    const selects = document.querySelectorAll('select[required]');
    
    selects.forEach(select => {
        select.addEventListener('change', function() {
            const container = getErrorContainer(this);
            
            if (this.value === '' || this.value === null) {
                this.classList.add('input-error');
                showFieldError(this, container, 'Please select an option', '', 'select');
            } else {
                this.classList.remove('input-error');
                clearFieldError(this, container);
            }
        });
    });
    
    console.log('✓ Select validation initialized for', selects.length, 'dropdowns');
}

/**
 * Character counter for textareas
 */
function initializeCharacterCounters() {
    const textareas = document.querySelectorAll('textarea[minlength], textarea[maxlength]');
    
    textareas.forEach(textarea => {
        const minLength = parseInt(textarea.getAttribute('minlength') || 0);
        const maxLength = parseInt(textarea.getAttribute('maxlength') || 999999);
        
        const createCounter = () => {
            const counterId = `char-count-${textarea.id}`;
            let counter = document.getElementById(counterId);
            
            if (!counter) {
                counter = document.createElement('div');
                counter.id = counterId;
                counter.className = 'character-counter';
                textarea.parentNode.insertBefore(counter, textarea.nextSibling);
            }
            
            const currentLength = textarea.value.length;
            const remaining = maxLength - currentLength;
            let counterText = `${currentLength} / ${maxLength}`;
            
            if (minLength > 0 && currentLength < minLength) {
                counterText += ` (need ${minLength - currentLength} more)`;
                counter.className = 'character-counter counter-warning';
            } else if (remaining < 100) {
                counter.className = 'character-counter counter-alert';
            } else {
                counter.className = 'character-counter';
            }
            
            counter.textContent = counterText;
        };
        
        textarea.addEventListener('input', createCounter);
        createCounter(); // Initialize on page load
    });
    
    console.log('✓ Character counters initialized for', textareas.length, 'textareas');
}

/**
 * Initialize all form validation features
 */
function initializeAllFormValidation() {
    console.log('🚀 Initializing live form validation...');
    initializeLiveFormValidation();
    initializeFormSubmitValidation();
    initializePasswordMatching();
    initializeSelectValidation();
    initializeCharacterCounters();
    console.log('✅ All form validation features initialized!');
}

/**
 * Run initialization when DOM is ready
 */
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeAllFormValidation);
} else {
    initializeAllFormValidation();
}

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        validateFieldLive,
        validateFormBeforeSubmit,
        initializeAllFormValidation
    };
}

