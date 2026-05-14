# CyberNova Analytics Ltd - System Requirements Document

**Project:** CyberNova Analytics Web Application  
**Date:** May 8, 2026  
**Version:** 1.0

---

## FUNCTIONAL REQUIREMENTS

### CLIENT / CUSTOMER SIDE (5 Requirements)

#### FR1: Submit Security Requests
- **Description:** Clients can submit security issue requests through a comprehensive form.
- **Details:**
  - Capture client information (name, email, phone, organization, country, job title)
  - Select security issue type (Phishing Response, Incident Response, Vulnerability Assessment, Cloud Hardening, Security Awareness Training, Compliance Review)
  - Provide detailed description of the technical problem
  - System automatically assigns request status as "NEW"
  - Confirmation page displays after successful submission
  
#### FR2: Submit Client Testimonials
- **Description:** Clients can submit testimonials about services received for admin approval.
- **Details:**
  - Capture client information (name, email, phone, organization, country, job title)
  - Select service type received (Ransomware Detection, DDoS Protection, Vulnerability Assessment, etc.)
  - Rate service on 5-point scale (1-5)
  - Provide testimonial title and detailed feedback text
  - Submit for admin review before public display
  - Receive confirmation of submission

#### FR3: Browse Solutions and Services Catalog
- **Description:** Clients can view published cybersecurity solutions and services.
- **Details:**
  - View solutions page with published solution cards
  - Click on individual solutions for detailed information
  - Each solution displays title, description, and external resources
  - Solutions are dynamically managed by admins (published/hidden status)

#### FR4: View Case Studies and Success Stories
- **Description:** Clients can access published case studies demonstrating CyberNova's expertise.
- **Details:**
  - View case studies page listing all published case studies
  - Click case study for detailed view
  - Case studies show real-world problem resolution examples
  - Admin controls publication status

#### FR5: Browse Security Blog and Articles
- **Description:** Clients can read security insights and industry trends through published blog articles.
- **Details:**
  - View cybersecurity blog page with article listings
  - Click articles for full-text reading
  - Articles cover phishing threats, cloud security, compliance regulations
  - Admin controls article publication and visibility

#### FR6: View Workshop Events and Gallery
- **Description:** Clients can discover and view workshop events and cybersecurity training materials.
- **Details:**
  - Browse active workshop events with details (title, date, location, capacity, instructor)
  - View gallery images associated with events
  - Click event details for expanded information
  - Gallery provides visual documentation of past workshops

#### FR7: Read Client Testimonials
- **Description:** Clients can read published testimonials from other satisfied clients.
- **Details:**
  - View testimonials page with approved client feedback
  - Each testimonial displays rating (1-5 stars), quote, client name, organization, and role
  - Testimonials filtered to show only published items
  - Displays count of positive feedback and trust signals

#### FR8: Access Interactive Chatbot
- **Description:** Website visitors can use a floating chatbot for instant FAQ assistance.
- **Details:**
  - Rule-based keyword matching chatbot (no AI required)
  - Responds to inquiries about testimonials, security, gallery, admin access
  - Provides quick-reply suggestions
  - Available on all public pages (hidden on admin pages)
  - Message history persisted during session

---

### ADMIN SIDE (8 Requirements)

#### FR9: Admin Authentication and Authorization
- **Description:** Secure admin login system with username/password authentication.
- **Details:**
  - Admins log in with username and password
  - Session management to protect admin areas
  - Admin logout functionality
  - Redirect unauthenticated users to login page
  - Display success messages for registration and login

#### FR10: Register New Administrator Accounts
- **Description:** Existing admins can create new administrator accounts.
- **Details:**
  - Admin registration form with username, email, full name
  - Password validation (minimum 8 characters)
  - Password confirmation matching
  - Success message and redirect to dashboard
  - Error handling for duplicate usernames

#### FR11: View Analytics Dashboard
- **Description:** Dashboard displays key metrics and analytics about security requests.
- **Details:**
  - Display total security requests count
  - Show top issue type submitted by clients
  - Display top region/country requesting services
  - Analytics cards with large bold numbers
  - Data automatically aggregated from security requests

#### FR12: Manage Dynamic Content Cards
- **Description:** Admins can create, edit, hide, and delete content cards for solutions, case studies, and blog.
- **Details:**
  - Add new card: title, description, image URL, external link, page type, status
  - Edit existing card: modify all fields
  - Delete card from system
  - Filter cards by page type (Solutions, Case Studies, Blog)
  - Toggle card status between Published and Hidden
  - Display creation timestamp for all cards

#### FR13: Approve/Hide Client Testimonials
- **Description:** Admins review and manage testimonial publication status.
- **Details:**
  - View submitted testimonials with status (Published/Pending/Hidden)
  - Approve pending testimonials for public display
  - Hide previously published testimonials
  - Delete testimonials if necessary
  - Filter testimonials by approval status
  - Display client information and ratings

#### FR14: Manage Workshop Events
- **Description:** Admins create, update, and remove workshop events from system.
- **Details:**
  - Add new workshop event: title, date/time, description, location, capacity, instructor, event type
  - View list of active workshop events
  - Remove events from active list (deactivate)
  - Edit event details
  - Events become available in image upload dropdown once created

#### FR15: Manage Gallery Images
- **Description:** Admins upload and organize images for workshop gallery.
- **Details:**
  - Add gallery images by providing image URL and optional title
  - Associate images with specific workshop events
  - Select events from dropdown of active events only
  - Delete gallery images
  - View all gallery images with event association and image preview

#### FR16: View and Filter Security Requests
- **Description:** Admins can review submitted security requests with advanced filtering.
- **Details:**
  - View table of all submitted security requests
  - Filter by issue type (dropdown selection)
  - Filter by country (text input with datalist suggestions)
  - Filter by search term (name, organization, job title)
  - View request details: client name, organization, country, issue type, status, submission date
  - Click "View Details" to see full request information and description
  - View regional demand analytics showing request distribution by region/country

---

## NON-FUNCTIONAL REQUIREMENTS

### NFR1: Security
- **Description:** System must protect user data and admin operations from unauthorized access.
- **Details:**
  - Passwords hashed using bcrypt or SHA-256 with salt
  - Session management with secure cookies (HttpOnly, Secure flags)
  - protect against SQL injection through parameterized queries
  - CSRF token validation on forms
  - Admin pages require authentication before access
  - Input validation on all forms (client-side and server-side)
  - Prevent sensitive data (passwords, credentials) in log files

### NFR2: Performance
- **Description:** System must respond to user actions quickly and load efficiently.
- **Details:**
  - Page load time < 2 seconds for all public pages
  - Admin dashboard load time < 1.5 seconds
  - Database queries optimized with indexes on frequently searched fields
  - Asset versioning implemented for cache busting
  - Images CDN-ready with responsive sizing
  - Pagination for large data tables (limit 20-50 rows per page)

### NFR3: User Experience (UX)
- **Description:** System provides intuitive, modern, and accessible user interface.
- **Details:**
  - Responsive design adapting to all device sizes (mobile, tablet, desktop)
  - Modern gradient UI with clear visual hierarchy
  - Consistent color scheme (dark blue #0A1F44 + gold #F2A900)
  - Smooth animations and transitions
  - Intuitive navigation with mobile hamburger menu
  - Clear error messages guiding users to correction
  - Success notifications confirming actions
  - Accessible buttons and forms with proper labels

### NFR4: Reliability and Data Integrity
- **Description:** System ensures accurate data storage and consistent operation.
- **Details:**
  - Database constraints preventing invalid data entry
  - Transaction management for multi-step operations
  - Backup mechanisms for critical data
  - Error logging for troubleshooting
  - Graceful handling of database connection failures
  - Data validation before persistence
  - Proper handling of concurrent requests
  - Recovery mechanisms from system failures

### NFR5: Scalability
- **Description:** System architecture supports growth in users and data volume.
- **Details:**
  - Database design normalized to support large datasets
  - Connection pooling for efficient database resource usage
  - Stateless architecture allowing horizontal scaling
  - Efficient filtering and pagination for data tables
  - Caching strategy for frequently accessed content
  - Support for future microservices migration
  - Modular code structure supporting feature additions

### NFR6: Maintainability
- **Description:** System code is clean, documented, and easy to modify.
- **Details:**
  - Clear separation of concerns (DAO, Model, Service, Servlet layers)
  - Consistent naming conventions across codebase
  - Javadoc comments on classes and public methods
  - Configuration externalization (database connection strings)
  - Constructor/dependency injection patterns
  - Reusable utility functions avoiding code duplication
  - Version control with meaningful commit messages

### NFR7: Responsive Design
- **Description:** Website displays correctly on all screen sizes and devices.
- **Details:**
  - Mobile-first responsive design approach
  - Breakpoints at 768px for mobile/tablet transition
  - Touch-friendly button sizes (minimum 48x48 pixels)
  - Hamburger menu on mobile (triggers at ≤768px)
  - Tables switch to card layout on mobile
  - Optimized font sizes for readability
  - Flexible grid layout using CSS Grid / Flexbox
  - Full-width viewport for maximum screen utilization

### NFR8: Accessibility (WCAG Compliance)
- **Description:** System is usable by people with disabilities (vision, hearing, motor).
- **Details:**
  - ARIA labels on interactive elements
  - Semantic HTML structure (nav, main, section tags)
  - Color contrast ratios meeting WCAG AA standards
  - Keyboard navigation support throughout application
  - Skip-to-content link for keyboard users
  - Form labels properly associated with inputs
  - Alt text descriptions for images
  - Screen reader friendly navigation structure
  - Focus indicators visible on keyboard navigation

### NFR9: Data Consistency and Validation
- **Description:** System enforces data quality through validation rules.
- **Details:**
  - Email format validation (RFC 5322 patterns)
  - Phone number format validation
  - Required field validation on all forms
  - Numeric field validation (ratings 1-5, capacity > 0)
  - Date/time validation for workshop events
  - Character length limits on text fields
  - Dropdown selections preventing invalid values
  - Database constraints as secondary validation layer

### NFR10: Monitoring and Analytics
- **Description:** System tracks usage patterns and provides insights for optimization.
- **Details:**
  - Admin dashboard displays key metrics (total requests, top issues, top regions)
  - Regional demand data showing geographic distribution
  - Request status tracking (NEW, IN_PROGRESS, RESOLVED, CLOSED)
  - Testimonial submission and approval metrics
  - Admin action logging for audit trail
  - Error tracking and reporting
  - Future readiness for analytics extensions

---

## SUMMARY

### Total Requirements
- **Functional Requirements: 16** (8 Client-side, 8 Admin-side)
- **Non-Functional Requirements: 10** (Security, Performance, UX, Reliability, Scalability, Maintainability, Responsive Design, Accessibility, Data Validation, Monitoring)

### Technology Stack
- **Framework:** Jakarta EE (Servlet/JSP)
- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Database:** MySQL 5.7+
- **Server:** Apache Tomcat 9.0+
- **IDE:** JetBrains IntelliJ IDEA

### Key Features Implemented
✅ Dynamic content management system  
✅ Role-based access control (Admin/Client)  
✅ Responsive mobile-first design  
✅ Modern UI with gradients and animations  
✅ Rule-based chatbot for customer support  
✅ Security request tracking and analytics  
✅ Client testimonial management system  
✅ Workshop event and gallery management  

---

*Document prepared for CyberNova Analytics Ltd development team*  
*Last updated: May 8, 2026*

