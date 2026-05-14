# Input Validation - Developer Guide & Code Examples

## Overview

This guide shows developers how to use the ValidationUtil class and understand the validation flow in the CyberNova Analytics application.

---

## Part 1: Using ValidationUtil in Servlets

### Basic Pattern

```java
import com.taskproject.pd_webapp.util.ValidationUtil;

@WebServlet("/submit-testimonial")
public class SubmitTestimonialServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Step 1: Get and trim input
        String name = ValidationUtil.safeTrim(request.getParameter("name"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        
        // Step 2: Validate
        String error = ValidationUtil.validateTestimonial(
            name, email, phone, org, country, jobTitle, 
            serviceType, rating, title, feedback
        );
        
        // Step 3: Handle error
        if (error != null) {
            String encoded = URLEncoder.encode(error, StandardCharsets.UTF_8);
            response.sendRedirect(contextPath + "/form?error=" + encoded);
            return;
        }
        
        // Step 4: Process (database, etc.)
        // ... trusted data, safe to use ...
        
        // Step 5: Redirect
        response.sendRedirect(contextPath + "/success");
    }
}
```

---

## Part 2: Adding New Validations

### Scenario: Add a new form field "website"

#### Step 1: Add helper method to ValidationUtil

```java
/**
 * Validates a website URL
 * Must start with http:// or https://
 */
public static boolean isValidWebsite(String website) {
    if (!isRequired(website)) return false;
    String lower = website.trim().toLowerCase();
    if (!lower.startsWith("http://") && !lower.startsWith("https://")) {
        return false;
    }
    try {
        new URI(website.trim()).toURL();
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

#### Step 2: Add composite validation method

```java
public static String validateMyForm(String field1, String field2, String website) {
    // ... validate other fields ...
    
    if (!isBlank(website) && !isValidWebsite(website)) {
        return "Please enter a valid website URL (http:// or https://).";
    }
    
    return null; // No errors
}
```

#### Step 3: Use in servlet

```java
String website = ValidationUtil.safeTrim(request.getParameter("website"));
String error = ValidationUtil.validateMyForm(field1, field2, website);

if (error != null) {
    response.sendRedirect(contextPath + "/form?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8));
    return;
}
```

#### Step 4: Add to JSP form

```html
<label>
    Website
    <input type="url" name="website" placeholder="https://example.com">
    <small>Optional. Must start with http:// or https://</small>
</label>
```

---

## Part 3: Form-by-Form Examples

### Example 1: Security Request Form

**JSP:**
```html
<form method="post" action="${contextPath}/submit-security-request" novalidate>
    <label>
        Name
        <input type="text" name="name" required 
               pattern="^[a-zA-Z\s'-]{2,}" maxlength="100"
               title="Letters, spaces, hyphens, apostrophes only">
    </label>
    
    <label>
        Email
        <input type="email" name="email" required maxlength="255">
    </label>
    
    <label>
        Description
        <textarea name="description" required 
                  minlength="20" maxlength="5000"
                  title="Between 20-5000 characters"></textarea>
    </label>
    
    <button type="submit">Submit</button>
</form>
```

**Servlet:**
```java
public class InquirySubmitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = ValidationUtil.safeTrim(request.getParameter("name"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String phone = ValidationUtil.safeTrim(request.getParameter("phone"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        // ... more fields ...
        
        String error = ValidationUtil.validateSecurityRequest(
            name, email, phone, organization, country, jobTitle, issueType, description
        );
        
        if (error != null) {
            String encoded = URLEncoder.encode(error, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/contact?error=" + encoded);
            return;
        }
        
        // Save to database
        SecurityRequest req = new SecurityRequest();
        req.setClientName(name);
        req.setClientEmail(email);
        // ... set other fields ...
        repository.save(req);
        
        response.sendRedirect(request.getContextPath() + "/thank-you");
    }
}
```

**ValidationUtil Method:**
```java
public static String validateSecurityRequest(String name, String email, String phone, 
                                               String organization, String country, 
                                               String jobTitle, String issueType, 
                                               String description) {
    if (!isRequired(name)) return "Name is required.";
    if (!isValidName(name)) return "Name contains invalid characters.";

    if (!isRequired(email)) return "Email is required.";
    if (!isValidEmail(email)) return "Please enter a valid email address.";

    if (!isRequired(phone)) return "Phone is required.";
    if (!isValidPhone(phone)) return "Please enter a valid phone number.";

    if (!isRequired(description)) return "Description is required.";
    if (!hasMinLength(description, 20)) return "Description must be at least 20 characters long.";
    if (!hasMaxLength(description, 5000)) return "Description is too long (max 5000 characters).";

    // ... validate other fields ...
    
    return null; // All valid
}
```

**Test Cases:**
```
Input: name="John O'Brien"
Result: ✓ Valid (apostrophe allowed)

Input: name="John123"
Result: ✗ Error: "Name contains invalid characters."

Input: email="user@example.com"
Result: ✓ Valid

Input: email="user@example"
Result: ✗ Error: "Please enter a valid email address."

Input: phone="+267 123 4567"
Result: ✓ Valid (length ≥ 10 after removing spaces)

Input: phone="123"
Result: ✗ Error: "Please enter a valid phone number."

Input: description="Some issue"
Result: ✗ Error: "Description must be at least 20 characters long."

Input: description="This is a detailed description of my security issue..."
Result: ✓ Valid
```

---

### Example 2: Admin Registration Form

**JSP:**
```html
<form method="post" action="${contextPath}/admin/register" novalidate>
    <label>
        Username
        <input type="text" name="username" required 
               minlength="3" maxlength="20"
               pattern="^[a-zA-Z0-9_-]{3,20}$"
               title="3-20 chars (letters, numbers, hyphens, underscores only)">
    </label>
    
    <label>
        Email
        <input type="email" name="email" required maxlength="255">
    </label>
    
    <label>
        Full Name
        <input type="text" name="fullName" required 
               pattern="^[a-zA-Z\s'-]{2,}" maxlength="255"
               title="Letters, spaces, hyphens, apostrophes only">
    </label>
    
    <label>
        Password
        <input type="password" name="password" required minlength="8">
    </label>
    
    <label>
        Confirm Password
        <input type="password" name="confirmPassword" required minlength="8">
    </label>
    
    <button type="submit">Register</button>
</form>
```

**Servlet:**
```java
public class AdminRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = ValidationUtil.safeTrim(request.getParameter("username"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String fullName = ValidationUtil.safeTrim(request.getParameter("fullName"));
        String password = ValidationUtil.safeTrim(request.getParameter("password"));
        String confirmPassword = ValidationUtil.safeTrim(request.getParameter("confirmPassword"));
        
        String error = ValidationUtil.validateAdminRegistration(
            username, email, fullName, password, confirmPassword
        );
        
        if (error != null) {
            forwardWithError(request, response, error);
            return;
        }
        
        // Check duplicates
        if (adminUserDAO.usernameExists(username)) {
            forwardWithError(request, response, "That username is already in use.");
            return;
        }
        
        if (adminUserDAO.emailExists(email)) {
            forwardWithError(request, response, "That email is already registered.");
            return;
        }
        
        // Create admin user
        AdminUser admin = new AdminUser();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setFullName(fullName);
        admin.setPasswordHash(AdminUserDAO.hashPassword(password));
        admin.setActive(true);
        
        adminUserDAO.createAdminUser(admin);
        response.sendRedirect(request.getContextPath() + "/admin/login?success=adminRegistered");
    }
    
    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message) {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/register-admin.jsp").forward(request, response);
    }
}
```

**ValidationUtil Method:**
```java
public static String validateAdminRegistration(String username, String email, String fullName,
                                                 String password, String confirmPassword) {
    if (!isRequired(username)) return "Username is required.";
    if (!isValidUsername(username)) 
        return "Username must be 3-20 characters (letters, numbers, hyphens, underscores only).";

    if (!isRequired(email)) return "Email is required.";
    if (!isValidEmail(email)) return "Please enter a valid email address.";

    if (!isRequired(fullName)) return "Full name is required.";
    if (!isValidName(fullName)) return "Full name contains invalid characters.";
    if (!hasMaxLength(fullName, 255)) return "Full name is too long (max 255 characters).";

    if (!isRequired(password)) return "Password is required.";
    if (!isValidPassword(password)) return "Password must be at least 8 characters long.";

    if (!isRequired(confirmPassword)) return "Password confirmation is required.";
    if (!passwordsMatch(password, confirmPassword)) return "Passwords do not match.";

    return null; // All valid
}
```

**Test Cases:**
```
Input: username="AD"
Result: ✗ Error: "Username must be 3-20 characters..."

Input: username="admin@user"
Result: ✗ Error (@ not allowed)

Input: username="admin-user_2"
Result: ✓ Valid

Input: password="pass", confirmPassword="pass"
Result: ✗ Error: "Password must be at least 8 characters..."

Input: password="securePass123", confirmPassword="securePass456"
Result: ✗ Error: "Passwords do not match."

Input: fullName="123Jack"
Result: ✗ Error: "Full name contains invalid characters."

Input: fullName="Jack O'Brien"
Result: ✓ Valid
```

---

### Example 3: Content Card Form

**JSP:**
```html
<form method="post" action="${contextPath}/admin/content-cards/add" novalidate>
    <label>
        Page Type
        <select name="pageType" required>
            <option value="">-- Select Type --</option>
            <option value="SOLUTION">Cybersecurity Solutions</option>
            <option value="CASE_STUDY">Case Studies</option>
            <option value="BLOG">Cyber Blog</option>
        </select>
    </label>
    
    <label>
        Card Title
        <input type="text" name="title" required 
               minlength="3" maxlength="200"
               title="3-200 characters">
    </label>
    
    <label>
        Description
        <textarea name="description" required 
                  minlength="10" maxlength="2000"
                  title="10-2000 characters"></textarea>
    </label>
    
    <label>
        Image URL
        <input type="url" name="imageUrl" 
               title="Must start with http:// or https://">
        <small>Optional. Must be a valid URL.</small>
    </label>
    
    <label>
        External Link
        <input type="url" name="externalLink"
               title="Must start with http:// or https://">
        <small>Optional. Must be a valid URL.</small>
    </label>
    
    <button type="submit">Add Card</button>
</form>
```

**Servlet:**
```java
public class AddContentCardServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String pageType = ValidationUtil.safeTrim(request.getParameter("pageType"));
        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        String imageUrl = ValidationUtil.safeTrim(request.getParameter("imageUrl"));
        String externalLink = ValidationUtil.safeTrim(request.getParameter("externalLink"));
        
        String error = ValidationUtil.validateContentCard(
            pageType, title, description, imageUrl, externalLink
        );
        
        if (error != null) {
            String encoded = URLEncoder.encode(error, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/content-cards?error=" + encoded);
            return;
        }
        
        ContentCard card = new ContentCard();
        card.setPageType(pageType);
        card.setTitle(title);
        card.setDescription(description);
        card.setImageUrl(ValidationUtil.isBlank(imageUrl) ? null : imageUrl);
        card.setExternalLink(ValidationUtil.isBlank(externalLink) ? null : externalLink);
        card.setStatus("PUBLISHED");
        
        contentCardDAO.addCard(card);
        response.sendRedirect(request.getContextPath() + "/admin/content-cards?success=cardAdded");
    }
}
```

**ValidationUtil Method:**
```java
public static String validateContentCard(String pageType, String title, String description,
                                          String imageUrl, String externalLink) {
    if (!isRequired(pageType)) return "Page type is required.";
    if (!isValidPageType(pageType)) return "Invalid page type selected.";

    if (!isRequired(title)) return "Card title is required.";
    if (!hasMinLength(title, 3)) return "Title must be at least 3 characters long.";
    if (!hasMaxLength(title, 200)) return "Title is too long (max 200 characters).";

    if (!isRequired(description)) return "Description is required.";
    if (!hasMinLength(description, 10)) return "Description must be at least 10 characters long.";
    if (!hasMaxLength(description, 2000)) return "Description is too long (max 2000 characters).";

    if (!isBlank(imageUrl) && !isValidUrl(imageUrl)) {
        return "Please enter a valid image URL starting with http:// or https://.";
    }

    if (!isBlank(externalLink) && !isValidUrl(externalLink)) {
        return "Please enter a valid external link starting with http:// or https://.";
    }

    return null; // All valid
}
```

**Test Cases:**
```
Input: pageType="" (empty)
Result: ✗ Error: "Page type is required."

Input: title="AI" (2 chars)
Result: ✗ Error: "Title must be at least 3 characters long."

Input: description="Short" (5 chars)
Result: ✗ Error: "Description must be at least 10 characters long."

Input: imageUrl="example.jpg" (no http://)
Result: ✗ Error: "Please enter a valid image URL..."

Input: imageUrl="https://example.com/image.jpg"
Result: ✓ Valid (if description is also ≥ 10 chars)

Input: externalLink=""
Result: ✓ Valid (optional field)
```

---

## Part 4: Common Mistakes & Solutions

### ❌ Mistake 1: Not trimming input

```java
// BAD
String name = request.getParameter("name");
if (!name.isEmpty()) { // Could be null!
    // ...
}

// GOOD
String name = ValidationUtil.safeTrim(request.getParameter("name"));
if (!ValidationUtil.isBlank(name)) {
    // ...
}
```

### ❌ Mistake 2: Only frontend validation

```java
// BAD - Security risk!
<input type="email" name="email" required>
// No backend validation

// GOOD
<input type="email" name="email" required>
// AND in servlet:
if (!ValidationUtil.isValidEmail(email)) {
    // Reject request
}
```

### ❌ Mistake 3: Un-encoded error messages

```java
// BAD - XSS vulnerability
response.sendRedirect("/form?error=" + errorMessage);

// GOOD
String encoded = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
response.sendRedirect("/form?error=" + encoded);
```

### ❌ Mistake 4: Case-sensitive enum/dropdown values

```java
// BAD - User selects "solution" but code checks "SOLUTION"
if (pageType.equals("SOLUTION")) { // Fails for "solution"

// GOOD
if (pageType.equalsIgnoreCase("SOLUTION")) {
    // Or standardize on one case
}
```

### ❌ Mistake 5: Repeating validation logic

```java
// BAD - Copy-paste validation in multiple servlets
if (!email.contains("@")) { /* error */ }
// Again in another servlet...
if (!email.contains("@")) { /* error */ }

// GOOD
// Use ValidationUtil.isValidEmail() everywhere
```

---

## Part 5: Adding Custom Validators

### Example: Phone number validation by country

```java
public static boolean isValidPhoneForCountry(String phone, String countryCode) {
    String cleaned = phone.replaceAll("[\\s+()-]", "");
    
    switch (countryCode.toUpperCase()) {
        case "US":
            // US: 10 digits
            return cleaned.matches("^\\d{10}$");
        case "UK":
            // UK: 10 digits
            return cleaned.matches("^\\d{10}$");
        case "BW": // Botswana
            // Botswana: 8 digits
            return cleaned.matches("^\\d{8}$");
        default:
            // Generic: 10-15 digits
            return cleaned.matches("^\\d{10,15}$");
    }
}

// Then use in servlet:
String error = isValidPhoneForCountry(phone, country) 
    ? null 
    : "Invalid phone number for " + country;
```

---

## Part 6: Testing Validation

### Unit Test Example

```java
@Test
public void testValidEmail() {
    assertTrue(ValidationUtil.isValidEmail("user@example.com"));
    assertTrue(ValidationUtil.isValidEmail("test+tag@domain.co.uk"));
}

@Test
public void testInvalidEmail() {
    assertFalse(ValidationUtil.isValidEmail("user@"));
    assertFalse(ValidationUtil.isValidEmail("@example.com"));
    assertFalse(ValidationUtil.isValidEmail("userexample.com"));
}

@Test
public void testValidName() {
    assertTrue(ValidationUtil.isValidName("John O'Brien"));
    assertTrue(ValidationUtil.isValidName("Mary-Jane Smith"));
}

@Test
public void testInvalidName() {
    assertFalse(ValidationUtil.isValidName("John123"));
    assertFalse(ValidationUtil.isValidName("张三")); // Chinese characters
}

@Test
public void testValidateSecurityRequest() {
    String error = ValidationUtil.validateSecurityRequest(
        "John Doe",
        "john@example.com",
        "+267123456789",
        "Acme Corp",
        "Botswana",
        "Manager",
        "Phishing Response",
        "This is a detailed description of my security issue..."
    );
    assertNull(error); // No errors
}

@Test
public void testValidateSecurityRequestErrors() {
    String error = ValidationUtil.validateSecurityRequest(
        "John123", // Invalid name (has numbers)
        "john@example.com",
        "+267123456789",
        "Acme Corp",
        "Botswana",
        "Manager",
        "Phishing Response",
        "Valid description..."
    );
    assertNotNull(error);
    assertTrue(error.contains("Name"));
}
```

---

## Summary

✅ **Always use ValidationUtil** for consistency  
✅ **Validate on both frontend and backend**  
✅ **URL-encode error messages** for security  
✅ **Use safeTrim()** for all user input  
✅ **Provide user-friendly error messages**  
✅ **Test edge cases** (empty, null, special chars)  
✅ **Check for duplicates** in database (usernames, emails)  

---

*Last Updated: May 12, 2026*

