# HTMLSanitizer Usage Guide

## Quick Reference

### For Text Fields (Names, Organizations, Job Titles, etc.)
```java
String sanitizedInput = HTMLSanitizer.sanitizeTextField(userInput);
```

### For Large Text Areas (Descriptions, Feedback, Comments)
```java
String sanitizedInput = HTMLSanitizer.sanitizeTextarea(userInput);
```

### For Database Storage
```java
String sanitizedInput = HTMLSanitizer.sanitizeForDatabase(userInput, "fieldName");
```

### For HTML Display
```java
String sanitizedForDisplay = HTMLSanitizer.sanitizeForDisplay(userInput);
```

---

## Common Usage Patterns

### Pattern 1: Simple Field Sanitization
```java
String organization = ValidationUtil.safeTrim(request.getParameter("organization"));

// Sanitize before storage
String sanitized = HTMLSanitizer.sanitizeTextField(organization);
model.setOrganization(sanitized);
```

### Pattern 2: With Validation
```java
String organization = ValidationUtil.safeTrim(request.getParameter("organization"));

// Validate first
String validationError = ValidationUtil.validateField(organization);
if (validationError != null) {
    return error;
}

// Then sanitize
String sanitized = HTMLSanitizer.sanitizeTextField(organization);
model.setOrganization(sanitized);
```

### Pattern 3: With Error Handling
```java
try {
    String sanitized = HTMLSanitizer.sanitizeTextField(userInput);
    model.setField(sanitized);
} catch (IllegalArgumentException e) {
    // User tried to inject dangerous content
    return error(e.getMessage());
}
```

### Pattern 4: Optional Fields
```java
String location = ValidationUtil.safeTrim(request.getParameter("location"));

String sanitized = ValidationUtil.isBlank(location) ? 
    "" : HTMLSanitizer.sanitizeTextField(location);

model.setLocation(sanitized);
```

---

## Methods Explained

### sanitizeTextField(String input)
**Use for:** Names, organizations, job titles, cities, countries, etc.
- ✅ Escapes HTML characters
- ✅ Detects dangerous content
- ✅ Throws exception if dangerous content found
- ✅ Returns trimmed, escaped string

```java
String name = HTMLSanitizer.sanitizeTextField("John O'Brien");
// Returns: "John O&#39;Brien"
```

### sanitizeTextarea(String input)
**Use for:** Descriptions, feedback, comments, longer text
- ✅ Escapes HTML characters
- ✅ Preserves whitespace/line breaks
- ✅ Detects dangerous content
- ✅ Throws exception if dangerous content found

```java
String feedback = HTMLSanitizer.sanitizeTextarea("Great service!\nWill hire again.");
// Returns: "Great service!\\nWill hire again."
```

### sanitizeForDatabase(String input, String fieldName)
**Use for:** Critical data before database insertion
- ✅ Validates input
- ✅ Detects dangerous patterns
- ✅ Escapes HTML
- ✅ Includes field name in error message

```java
String sanitized = HTMLSanitizer.sanitizeForDatabase(email, "email_field");
```

### escapeHtml(String input)
**Use for:** When you want to handle exceptions yourself
- ✅ Only escapes, no validation
- ✅ Returns escaped string

```java
String escaped = HTMLSanitizer.escapeHtml(userInput);
```

### containsDangerousContent(String input)
**Use for:** Custom validation logic
- ✅ Returns boolean
- ✅ No exceptions thrown

```java
if (HTMLSanitizer.containsDangerousContent(input)) {
    // Handle dangerous content
}
```

### isSafe(String input)
**Use for:** Checking if sanitization is needed
- ✅ Returns boolean
- ✅ No exceptions thrown

```java
if (!HTMLSanitizer.isSafe(input)) {
    input = HTMLSanitizer.escapeHtml(input);
}
```

---

## What Gets Escaped

### HTML Special Characters
```
Input:  Company & Associates <Limited>
Output: Company &amp; Associates &lt;Limited&gt;
```

### Quotes
```
Input:  He said "hello"
Output: He said &quot;hello&quot;
```

### Single Quotes
```
Input:  It's fine
Output: It&#39;s fine
```

---

## What Gets Blocked/Detected

These patterns will trigger exceptions:

### Script Tags
```java
HTMLSanitizer.sanitizeTextField("<script>alert('xss')</script>")
// Throws: IllegalArgumentException
```

### Event Handlers
```java
HTMLSanitizer.sanitizeTextField("onclick='alert(1)'")
// Throws: IllegalArgumentException
```

### IFrames
```java
HTMLSanitizer.sanitizeTextField("<iframe src='evil.com'></iframe>")
// Throws: IllegalArgumentException
```

### JavaScript Protocol
```java
HTMLSanitizer.sanitizeTextField("javascript:alert('xss')")
// Throws: IllegalArgumentException
```

---

## Error Handling

When dangerous content is detected, `IllegalArgumentException` is thrown:

```java
try {
    String sanitized = HTMLSanitizer.sanitizeTextField(userInput);
    model.setField(sanitized);
} catch (IllegalArgumentException e) {
    // e.getMessage() = "HTML/JavaScript code detected in [field] field. Not allowed."
    response.sendRedirect("/page?error=" + e.getMessage());
}
```

---

## Complete Example: Contact Form

```java
@WebServlet("/submit-contact")
public class ContactFormServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get and trim input
        String name = ValidationUtil.safeTrim(request.getParameter("name"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String organization = ValidationUtil.safeTrim(request.getParameter("organization"));
        String country = ValidationUtil.safeTrim(request.getParameter("country"));
        String jobTitle = ValidationUtil.safeTrim(request.getParameter("jobTitle"));
        String message = ValidationUtil.safeTrim(request.getParameter("message"));
        
        // Validate all inputs
        String validationError = ValidationUtil.validateContact(
            name, email, organization, country, jobTitle, message
        );
        
        if (validationError != null) {
            response.sendRedirect("/contact?error=" + validationError);
            return;
        }
        
        try {
            // Sanitize text fields
            String sanitizedName = HTMLSanitizer.sanitizeTextField(name);
            String sanitizedOrganization = HTMLSanitizer.sanitizeTextField(organization);
            String sanitizedCountry = HTMLSanitizer.sanitizeTextField(country);
            String sanitizedJobTitle = HTMLSanitizer.sanitizeTextField(jobTitle);
            String sanitizedMessage = HTMLSanitizer.sanitizeTextarea(message);
            
            // Create model with sanitized data
            ContactRequest request = new ContactRequest();
            request.setName(sanitizedName);
            request.setEmail(email);  // Email validated separately
            request.setOrganization(sanitizedOrganization);
            request.setCountry(sanitizedCountry);
            request.setJobTitle(sanitizedJobTitle);
            request.setMessage(sanitizedMessage);
            
            // Save to database
            dao.save(request);
            
            response.sendRedirect("/thank-you");
            
        } catch (IllegalArgumentException e) {
            // Dangerous content detected
            response.sendRedirect("/contact?error=" + e.getMessage());
        } catch (Exception e) {
            response.sendRedirect("/contact?error=Failed to submit form");
        }
    }
}
```

---

## Best Practices

### ✅ DO:
1. **Sanitize on input** - Before storing in database
2. **Use appropriate method** - TextField for short text, Textarea for long
3. **Validate first** - Check format before sanitizing
4. **Escape on output** - When displaying user content in HTML
5. **Catch exceptions** - Handle dangerous content gracefully
6. **Log security events** - Track sanitization occurrences

### ❌ DON'T:
1. **Don't skip sanitization** - Even for "trusted" sources
2. **Don't use unescape** - Never reverse HTML escaping
3. **Don't trust client-side validation** - Always validate server-side
4. **Don't store raw HTML** - Always escape before storage
5. **Don't display user input raw** - Always escape for HTML context

---

## Comparison: Before and After

### Before Implementation
```java
String organization = request.getParameter("organization");
model.setOrganization(organization);  // ❌ UNSAFE - XSS vulnerable
```

### After Implementation
```java
String organization = ValidationUtil.safeTrim(request.getParameter("organization"));
try {
    String sanitized = HTMLSanitizer.sanitizeTextField(organization);
    model.setOrganization(sanitized);  // ✅ SAFE - XSS protected
} catch (IllegalArgumentException e) {
    // Handle dangerous content
}
```

---

## Performance Notes

- **sanitizeTextField()** - Fast, uses simple string replacement
- **sanitizeTextarea()** - Same performance as TextField
- **containsDangerousContent()** - Scans for 8 dangerous patterns
- **Performance:** < 1ms for typical inputs

The sanitization is performant enough for high-volume production use.

---

## Integration Checklist

When adding a new input field:

- [ ] Import HTMLSanitizer class
- [ ] Use ValidationUtil.safeTrim() first
- [ ] Call appropriate HTMLSanitizer method
- [ ] Add try-catch for IllegalArgumentException
- [ ] Test with dangerous inputs
- [ ] Verify in JSP it displays as escaped text
- [ ] Document the sanitization approach

---

## Questions?

Refer to these files for examples:
- `InquirySubmitServlet.java` - Contact form example
- `SubmitTestimonialServlet.java` - Multiple field example
- `AdminRegisterServlet.java` - Name sanitization example
- `HTMLSanitizer.java` - Complete implementation with tests

All files have inline comments and examples!

