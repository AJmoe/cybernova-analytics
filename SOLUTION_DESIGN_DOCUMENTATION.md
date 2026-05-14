# CyberNova Analytics Ltd - Solution Design Documentation

**Project:** CyberNova Analytics Web Application  
**Version:** 1.0  
**Date:** May 11, 2026  
**Status:** Complete

---

## TABLE OF CONTENTS

1. [Prototype Design](#prototype-design)
2. [System Documentation](#system-documentation)

---

# 1. PROTOTYPE DESIGN

## 1.1 Design Overview

The CyberNova Analytics platform follows a modern, mobile-first design approach with emphasis on user experience, accessibility, and professional aesthetics.

### Design Philosophy
- **Modern Aesthetic:** Gradient backgrounds, smooth animations, clean typography
- **Color Scheme:** Dark Blue (#0A1F44) + Gold (#F2A900) + White
- **Typography:** Inter/Segoe UI for clean, readable sans-serif experience
- **Spacing:** Consistent 1rem/16px baseline grid system
- **Accessibility:** WCAG AA compliance with proper contrast ratios

---

## 1.2 Wireframes & Layout Structures

### Public Pages Layout
```
┌─────────────────────────────────────────────────────┐
│  HEADER (Dark Blue Gradient)                         │
│  - Logo/Brand Name (Gradient Text)                   │
│  - Navigation Bubbles (Gold-Bordered Pills)          │
│  - Mobile Hamburger Menu (≤768px)                    │
└─────────────────────────────────────────────────────┘
│                                                       │
│  MAIN CONTENT AREA                                   │
│  - Hero Section (Large Hero Panel)                   │
│  - Content Sections (Grid Layouts)                   │
│  - CTA Sections (Call-to-Action Panels)              │
│                                                       │
└─────────────────────────────────────────────────────┘
│  FOOTER (Dark Blue)                                  │
│  - Links                                             │
│  - Copyright                                         │
│  - Chatbot Widget (Bottom Right)                     │
└─────────────────────────────────────────────────────┘
```

### Admin Pages Layout
```
┌─────────────────────────────────────────────────────┐
│  HEADER (Same as Public)                             │
└─────────────────────────────────────────────────────┘
│                                                       │
│  PAGE HEADING                                        │
│  - Eyebrow text (Small uppercase)                    │
│  - Page title with gradient                          │
│  - Description                                       │
│                                                       │
│  ANALYTICS CARDS / CONTENT SECTIONS                  │
│  - Dashboard Cards (3-column grid)                   │
│  - Forms (Multi-column responsive)                   │
│  - Data Tables (Full width with filtering)           │
│                                                       │
└─────────────────────────────────────────────────────┘
│  FOOTER (Dark Blue)                                  │
└─────────────────────────────────────────────────────┘
```

---

## 1.3 Component Design Specifications

### Navigation Bubble Component
```
DESKTOP VIEW:
┌─────────────────────────────────────────────────┐
│ Logo  ┌──────────────────────────────────────┐  │
│       │ [Home] [Solutions] [Blog] [Contact]  │  │
│       │ [Testimonials] [Gallery] [Admin]     │  │
│       └──────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘

MOBILE VIEW:
┌──────────────────────────┐
│ Logo        [☰ Menu]     │
├──────────────────────────┤
│ [Home]                   │
│ [Solutions]              │
│ [Blog]                   │
│ [Contact]                │
│ [Testimonials]           │
│ [Gallery]                │
│ [Admin]                  │
└──────────────────────────┘

STYLES:
- Container: Glass effect with backdrop blur
- Individual pill: Dark blue gradient (135deg)
- Border: 1.5px solid gold at 20% opacity
- Hover: Gold gradient overlay + lift effect (translateY -2px)
- Focus: Glowing shadow with gold accent
```

### Hero Panel Component
```
┌──────────────────────────────────────┐
│  🚀 EYEBROW TEXT (Uppercase)         │
│                                      │
│  MAIN HEADING (Large, Gradient)      │
│  Protect Your Digital Future         │
│                                      │
│  Description paragraph with          │
│  maximum width for readability       │
│                                      │
│  [Primary Button] [Secondary Button] │
└──────────────────────────────────────┘

DECORATIVE ELEMENTS:
- Background: Linear gradient white to light gray
- Decorative orbs: Radial gradients (gold/blue)
- Button styling: Gold gradient on primary

RESPONSIVE:
- Desktop: Full padding (4rem 2rem)
- Tablet: 3rem 1.5rem
- Mobile: 2rem 1rem
```

### Analytics Card Component
```
┌─────────────────────────┐
│ ━ Gold Left Border      │
│                         │
│ METRIC LABEL (Small)    │
│ Total Requests          │
│                         │
│ 156                     │
│ (Large Bold Number)     │
│                         │
│ Last Updated: May 11    │
└─────────────────────────┘

STYLES:
- Container: White gradient background
- Left border: 5px solid gold
- Number color: Blue (#2B5FBF)
- Hover: Lift up 6px, enhanced shadow
- Background gradient: Linear 135deg white to light gray
```

### Content Card Component
```
┌──────────────────────────┐
│ EMOJI                    │
│ 🛡️                       │
│                          │
│ Card Title               │
│                          │
│ Description with up to   │
│ 2-3 sentences about the  │
│ service or content       │
│                          │
│ [Learn More →] (Link)    │
└──────────────────────────┘

STYLES:
- Top border indicator: scales in on hover
- Shadow: 0 20px 50px on hover
- Transform: translateY -8px on hover
- Border: 2px transparent (gold on hover)
```

### Data Table Component
```
┌─────────────────────────────────────────────────┐
│ HEADER ROW (Dark Gradient, White Text)          │
│ Name | Organization | Country | Status | Action │
├─────────────────────────────────────────────────┤
│ Row 1 (White, Black Text)                       │
├─────────────────────────────────────────────────┤
│ Row 2 (Gold tint 3%, Black Text)                │
├─────────────────────────────────────────────────┤
│ Row 3 (White, Black Text) + Hover: Gold accent │
└─────────────────────────────────────────────────┘

STYLES:
- Header: Gradient background (blue gradient)
- Row text: Pure black (#000000) for contrast
- Striped: Alternating white/gold-tinted rows
- Hover: Gold left accent (4px) + background shift
- Responsive: Stack to cards on mobile
```

### Button Variants
```
PRIMARY BUTTON (Gold)
┌──────────────────────────┐
│ Get Started Today        │  Background: Gold gradient
│                          │  Color: Dark Blue text
│                          │  Hover: Darker gradient + lift
│                          │  Effect: Shine from left
└──────────────────────────┘

SECONDARY BUTTON (Outline)
┌──────────────────────────┐
│ View Solutions           │  Border: 2px gold outline
│                          │  Color: Dark Blue text
│                          │  Hover: Gold background tint
└──────────────────────────┘

DANGER BUTTON (Red)
┌──────────────────────────┐
│ Delete                   │  Background: Red gradient
│                          │  Color: White text
│                          │  Hover: Darker red gradient
└──────────────────────────┘
```

---

## 1.4 Responsive Design Breakpoints

### Breakpoint Strategy
```
MOBILE:     ≤ 768px
TABLET:     769px - 1024px  
DESKTOP:    ≥ 1025px
```

### Mobile-Specific Changes
- **Navigation:** Hamburger menu collapses full nav
- **Grids:** Single column layouts
- **Tables:** Convert to card format
- **Spacing:** Reduced padding (1rem instead of 2rem)
- **Typography:** Reduced font sizes by 0.1-0.2rem
- **Buttons:** Full-width on mobile
- **Hero:** Reduced padding, centered content

### Tablet-Specific Changes
- **Navigation:** Full nav visible + pill styling active
- **Grids:** 2-column layouts
- **Spacing:** Balanced padding
- **Tables:** Still scrollable but readable

### Desktop-Specific Changes
- **Navigation:** Full pill navigation with glass effect + 3rem gap
- **Grids:** 3+ column layouts
- **Spacing:** Maximum padding for breathing room
- **Tables:** Full display with all columns

---

## 1.5 User Flow Diagrams

### Client/Public User Flow
```
┌─────────────┐
│  Home Page  │
└──────┬──────┘
       │
       ├─→ [Browse Solutions] ─→ [Solution Detail]
       │
       ├─→ [View Case Studies] ─→ [Case Study Detail]
       │
       ├─→ [Read Blog] ─→ [Article Detail]
       │
       ├─→ [View Gallery] ─→ [Event Detail]
       │
       ├─→ [Contact Page]
       │   ├─→ [Submit Security Request] ─→ [Thank You]
       │   └─→ [Submit Testimonial] ─→ [Submitted for Review]
       │
       ├─→ [Testimonials Page] ─→ [View Published Reviews]
       │
       └─→ [Chatbot] (Floating widget on all pages)
```

### Admin User Flow
```
┌─────────────────┐
│ Admin Login     │
└────────┬────────┘
         │
         ├─→ [Dashboard]
         │   ├─→ [View Analytics Cards]
         │   ├─→ [View Security Requests Table]
         │   └─→ [View Regional Demand Table]
         │
         ├─→ [Manage Content Cards]
         │   ├─→ [Add Card]
         │   ├─→ [Edit Card]
         │   ├─→ [Delete Card]
         │   └─→ [Filter by Type]
         │
         ├─→ [Manage Testimonials]
         │   ├─→ [Approve Testimonial]
         │   ├─→ [Hide Testimonial]
         │   └─→ [Delete Testimonial]
         │
         ├─→ [Manage Workshop Events]
         │   ├─→ [Create Event]
         │   ├─→ [Edit Event]
         │   └─→ [Deactivate Event]
         │
         ├─→ [Manage Gallery Images]
         │   ├─→ [Add Image]
         │   └─→ [Delete Image]
         │
         ├─→ [Register New Admin]
         │
         └─→ [Logout]
```

---

## 1.6 UI Component Library

### Color Palette
```
PRIMARY BRAND COLORS:
- Dark Blue:    #0A1F44 (Header, main background)
- Gold:         #F2A900 (Accents, hover, highlights)
- White:        #FFFFFF (Panels, content areas)

SECONDARY COLORS:
- Blue:         #2B5FBF (Links, stats, highlights)
- Muted Gray:   #B9C4D6 (Secondary text)
- Light Gray:   #F5F7FB (Body text)
- Red:          #D32F2F (Danger/Delete actions)
- Green:        #22C55E (Success messages)

BACKGROUND GRADIENTS:
- Header: 135deg, #0A1F44 0%, #0F2C66 100%
- Hero: 135deg, #FFFFFF 0%, #F8FAFC 100%
- Hero CTA: 135deg, #0A1F44 0%, #2B5FBF 100%
- Testimonial: 135deg, #0A1F44 0%, #1A3A5F 100%

TRANSPARENCY:
- Overlay Dark: rgba(10, 31, 68, 0.65)
- Overlay Light: rgba(255, 255, 255, 0.08)
- Border Light: rgba(15, 44, 102, 0.15)
- Accent Light: rgba(242, 169, 0, 0.1)
```

### Typography System
```
FONT FAMILY:
Primary: Inter, Segoe UI, Arial, sans-serif

HEADING HIERARCHY:
H1 (hero.h2):    2.5rem, 800 weight, line-height 1.2
H2 (page-heading): 2.2rem, 800 weight
H3 (panel.h3):   1.4rem, 700 weight
H4 (normal):     1.1rem, 600 weight
Body:            1rem, 400 weight, line-height 1.6

SIZES:
- Large:         1.1rem
- Normal:        0.95rem
- Small:         0.85rem
- Tiny:          0.8rem (labels, badges)

WEIGHTS:
- Regular:       400
- Medium:        500
- Semibold:      600
- Bold:          700
- Extra Bold:    800
- Black:         900
```

### Spacing Scale
```
Base Unit: 1rem (16px)

Spacing:
- xs: 0.25rem (4px)
- sm: 0.5rem (8px)
- md: 1rem (16px)
- lg: 1.5rem (24px)
- xl: 2rem (32px)
- 2xl: 3rem (48px)
- 3xl: 4rem (64px)

Padding:
- Tight:   0.75rem
- Normal:  1rem
- Loose:   1.5rem
- Hero:    4rem 2rem
- Panel:   1.5rem

Gaps:
- Items:   0.5-0.75rem
- Sections: 1.5rem
- Blocks:  2-3rem
```

### Shadow System
```
Subtle:    0 4px 15px rgba(0, 0, 0, 0.1)
Medium:    0 8px 20px rgba(0, 0, 0, 0.15)
Deep:      0 15px 45px rgba(0, 0, 0, 0.12)
Card:      0 6px 20px rgba(0, 0, 0, 0.15)
Hover:     0 12px 32px rgba(242, 169, 0, 0.25)

Inset:     inset 0 0 20px rgba(242, 169, 0, 0.1)
```

### Border Radius
```
Small:     0.6rem
Medium:    0.8rem
Large:     1rem
XL:        1.2rem
Full:      999px (pills/fully rounded)

Pills:     border-radius: 999px
Cards:     border-radius: 1rem
Inputs:    border-radius: 0.8rem
Testimony: border-radius: 18px
```

---

## 1.7 Interactive States & Animations

### Button States
```
DEFAULT:
- Background: Gold gradient (135deg)
- Shadow: 0 4px 15px
- Transform: none

HOVER:
- Background: Darker gold gradient
- Shadow: 0 8px 20px + glow
- Transform: translateY(-2px)
- Animation: shine effect (left to right)
- Duration: 0.35s cubic-bezier(0.4, 0, 0.2, 1)

FOCUS:
- Outline: 2px solid accent
- Outline-offset: 2px
- Shadow: enhanced

ACTIVE:
- Transform: translateY(0)
- Shadow: reduced

DISABLED:
- Opacity: 0.6
- Cursor: not-allowed
```

### Navigation Link States
```
DEFAULT:
- Color: Light text
- Border: Gold at 20% opacity
- Background: Dark blue gradient
- Shadow: 0 8px 24px

HOVER:
- Border: Gold at 65% opacity
- Background: Gold/blue gradient
- Shadow: 0 12px 32px + inset glow
- Transform: translateY(-2px)
- Speed: 0.35s transition

ACTIVE (Current page):
- Same as hover state
- Permanent until navigation change
```

### Form Input States
```
DEFAULT:
- Border: 1.5px solid rgba(dark, 0.15)
- Background: White
- Color: Dark text

FOCUS:
- Border: Gold accent color
- Shadow: 0 0 0 3px rgba(gold, 0.15)
- Background: White
- Outline: none

FILLED:
- Color: Dark text visible

ERROR:
- Border: Red color
- Shadow: Red glow
- Message: Red text below
```

### Transition Timings
```
SLOW:     0.5s (major layout changes)
NORMAL:   0.3-0.35s (most hover, focus states)
FAST:     0.15-0.25s (small highlights)
INSTANT:  0s (active states)

EASING:
- Cubic Bezier: (0.4, 0, 0.2, 1) - standard ease
- Linear: for continuous animations
- Ease-in-out: for complex animations
```

### Hover Effects
```
CARDS:
- Translate: -8px (upward movement)
- Shadow: Increase to 0 20px 50px
- Duration: 0.4s

IMAGES:
- Scale: 1.05 (small zoom)
- Shadow: Increase
- Duration: 0.3s

TEXT LINKS:
- Color: Shift to gold/blue
- Underline: Appears or becomes bolder
- Duration: 0.3s
```

---

# 2. SYSTEM DOCUMENTATION

## 2.1 Architecture Overview

### High-Level System Architecture
```
┌─────────────────────────────────────────────────────────┐
│                       CLIENT LAYER                      │
│  (HTML5 + CSS3 + JavaScript - Vanilla)                  │
│  - Public Pages (Home, Solutions, Blog, Gallery, etc.)  │
│  - Admin Pages (Dashboard, Management UIs)              │
│  - Responsive Design (Mobile, Tablet, Desktop)          │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │ HTTP/HTTPS
                        │
┌───────────────────────v─────────────────────────────────┐
│                    WEB LAYER                            │
│           (Servlets / Jakarta EE 10)                    │
├─────────────────────────────────────────────────────────┤
│ - Request Routing (30+ Servlets for different routes)   │
│ - Session Management (Admin authentication)             │
│ - Form Processing & Validation                          │
│ - Business Logic Orchestration                          │
│ - Response Rendering (JSP Templates)                    │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │
┌───────────────────────v─────────────────────────────────┐
│                  BUSINESS/SERVICE LAYER                 │
│          (Java Classes + Data Access Objects)           │
├─────────────────────────────────────────────────────────┤
│ - POJO Models (SecurityRequest, Testimonial, etc.)      │
│ - DAOs (Data Access Objects)                            │
│ - Business Rules & Calculations                         │
│ - Utility Functions                                     │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │
┌───────────────────────v─────────────────────────────────┐
│               DATA PERSISTENCE LAYER                    │
│                (MySQL 5.7+)                             │
├─────────────────────────────────────────────────────────┤
│ - 13+ Data Tables (SecurityRequest, Testimonial, etc.)  │
│ - Indexes on frequently queried fields                  │
│ - Foreign Key Constraints                              │
│ - Transaction Management                               │
└─────────────────────────────────────────────────────────┘
```

### Deployment Stack
```
┌──────────────────────────────────────────┐
│      Apache Tomcat 9.0+                  │
│  (Java Application Server)               │
│  - Runs WAR file (pd_webapp.war)         │
│  - Manages Servlet/JSP lifecycle         │
│  - Session persistence                   │
│  - Request handling & routing            │
└──────────────────────────────────────────┘
         │
         │ Configured via context.xml
         │
┌──────────────────────────────────────────┐
│    MySQL 5.7+ Database Server            │
│  - 13 Tables for data persistence        │
│  - Connection pooling                    │
│  - ACID compliance                       │
│  - Backup & recovery ready               │
└──────────────────────────────────────────┘
```

---

## 2.2 Technology Stack Details

### Backend Technologies
```
FRAMEWORK:
- Jakarta EE 10 (Servlet API 6.0, JSP 3.1)
- Java 17 (LTS version)

BUILD TOOL:
- Apache Maven 3.8+
  - WAR packaging for Tomcat deployment
  - Dependency management for libraries
  - Compiler configuration (Java 17 source/target)

WEB SERVER:
- Apache Tomcat 9.0+
  - 8080 (default HTTP port)
  - Configurable for HTTPS/SSL
  - Session manager enabled
  - JNDI data source configuration
```

### Frontend Technologies
```
HTML/CSS/JavaScript:
- HTML5 semantic markup
- CSS3 with modern features (Grid, Flexbox, Gradients)
- Vanilla JavaScript (no frameworks)
- Responsive design with mobile-first approach

FRAMEWORKS & LIBRARIES:
- Bootstrap 5.3.3 (CSS framework for components)
- Bootstrap Icons 1.11.3 (Icon library)
- JSTL 3.0.1 (JSP Standard Tag Library)
- jQuery (optional, not heavily used)

ASSET MANAGEMENT:
- WebJars for CDN delivery of libraries
- Asset versioning (query params) for cache busting
- Image optimization with responsive sizing
```

### Database
```
DATABASE MANAGEMENT SYSTEM:
- MySQL 5.7.x (or 8.0.x for newer features)

CONNECTOR:
- MySQL Connector/J 9.7.0

FEATURES:
- InnoDB engine (ACID compliance)
- UTF-8 character encoding (full unicode support)
- Connection pooling via DataSource
- Prepared statements (SQL injection prevention)
```

---

## 2.3 Database Schema Design

### Entity-Relationship Diagram (ERD)
```
┌─────────────────────┐
│      admin_user     │
├─────────────────────┤
│ user_id (PK)        │
│ username (UNIQUE)   │
│ email               │
│ full_name           │
│ password_hash       │
│ created_at          │
└─────────────────────┘
         │
         │ (1 admin : many requests)
         │
┌─────────────────────────────────────┐
│      security_request               │
├─────────────────────────────────────┤
│ request_id (PK)                     │
│ client_id (FK)                      │
│ type_of_security_issue              │
│ description                         │
│ status (NEW/IN_PROGRESS/RESOLVED)   │
│ priority (LOW/MEDIUM/HIGH/CRITICAL) │
│ region                              │
│ assigned_to (FK to admin_user)      │
│ created_at                          │
│ updated_at                          │
│ resolved_at                         │
└─────────────────────────────────────┘
         ▲
         │ (1 client : many requests)
         │
┌─────────────────────────────────────┐
│          client                     │
├─────────────────────────────────────┤
│ client_id (PK)                      │
│ name                                │
│ email (UNIQUE)                      │
│ phone                               │
│ organization                        │
│ country                             │
│ job_title                           │
│ created_at                          │
│ updated_at                          │
└─────────────────────────────────────┘
         │
         │ (1 client : many testimonials)
         │
┌─────────────────────────────────────┐
│         testimonial                 │
├─────────────────────────────────────┤
│ testimonial_id (PK)                 │
│ client_id (FK)                      │
│ rating (1-5)                        │
│ title                               │
│ feedback_text                       │
│ service_type                        │
│ is_published (BOOLEAN)              │
│ published_date                      │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│       content_card                  │
├─────────────────────────────────────┤
│ card_id (PK)                        │
│ page_type (SOLUTION/CASE_STUDY/BLOG)│
│ title                               │
│ description                         │
│ image_url                           │
│ external_link                       │
│ status (PUBLISHED/HIDDEN)           │
│ created_at                          │
│ updated_at                          │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│      workshop_event                 │
├─────────────────────────────────────┤
│ event_id (PK)                       │
│ title                               │
│ event_date                          │
│ description                         │
│ location                            │
│ capacity                            │
│ instructor                          │
│ event_type                          │
│ created_at                          │
│ updated_at                          │
└─────────────────────────────────────┘
         │
         │ (1 event : many images)
         │
┌─────────────────────────────────────┐
│      workshop_gallery               │
├─────────────────────────────────────┤
│ gallery_id (PK)                     │
│ event_id (FK)                       │
│ image_url                           │
│ image_title                         │
│ created_at                          │
└─────────────────────────────────────┘
```

### Key Tables Details

#### admin_user Table
```sql
CREATE TABLE admin_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    full_name VARCHAR(100),
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username)
);
```

#### security_request Table
```sql
CREATE TABLE security_request (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    type_of_security_issue VARCHAR(100),
    description TEXT,
    status ENUM('NEW', 'IN_PROGRESS', 'RESOLVED', 'CLOSED') DEFAULT 'NEW',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    region VARCHAR(50),
    assigned_to INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (assigned_to) REFERENCES admin_user(user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);
```

#### client Table
```sql
CREATE TABLE client (
    client_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    organization VARCHAR(100),
    country VARCHAR(50),
    job_title VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_country (country)
);
```

#### testimonial Table
```sql
CREATE TABLE testimonial (
    testimonial_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(150),
    feedback_text TEXT,
    service_type VARCHAR(100),
    is_published BOOLEAN DEFAULT FALSE,
    published_date TIMESTAMP NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    INDEX idx_published (is_published)
);
```

---

## 2.4 Project Structure

### Directory Organization
```
pd_webapp/
├── src/main/
│   ├── java/com/taskproject/pd_webapp/
│   │   ├── dao/                          # Data Access Objects
│   │   │   ├── AdminUserDAO.java
│   │   │   ├── ClientDAO.java
│   │   │   ├── ContentCardDAO.java
│   │   │   ├── TestimonialDAO.java
│   │   │   └── GalleryDAO.java
│   │   │
│   │   ├── model/                        # POJO Data Models
│   │   │   ├── AdminUser.java
│   │   │   ├── Client.java
│   │   │   ├── SecurityRequest.java
│   │   │   ├── Testimonial.java
│   │   │   ├── ContentCard.java
│   │   │   ├── WorkshopEvent.java
│   │   │   └── WorkshopGallery.java
│   │   │
│   │   ├── web/servlet/                  # Request Handlers
│   │   │   ├── HomeServlet.java
│   │   │   ├── AdminLoginServlet.java
│   │   │   ├── AdminDashboardServlet.java
│   │   │   ├── ManageContentCardsServlet.java
│   │   │   ├── SubmitTestimonialServlet.java
│   │   │   ├── AdminManageTestimonialsServlet.java
│   │   │   ├── GalleryServlet.java
│   │   │   ├── SolutionsServlet.java
│   │   │   ├── ContactServlet.java
│   │   │   └── ... (30+ Servlets total)
│   │   │
│   │   ├── filter/                       # Request Filters
│   │   │   └── AdminAuthFilter.java      # Authentication/Authorization
│   │   │
│   │   ├── service/                      # Business Logic (future)
│   │   │
│   │   ├── util/                         # Utility Functions
│   │   │   └── ... (helpers, validators)
│   │   └── ...
│   │
│   ├── resources/
│   │   └── META-INF/beans.xml            # CDI configuration
│   │
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml                   # Servlet configuration
│       │   ├── jsp/
│       │   │   ├── home.jsp
│       │   │   ├── contact.jsp
│       │   │   ├── testimonials.jsp
│       │   │   ├── solutions.jsp
│       │   │   ├── articles.jsp
│       │   │   ├── gallery.jsp
│       │   │   ├── common/
│       │   │   │   ├── header.jsp        # Shared header
│       │   │   │   └── footer.jsp        # Shared footer + chatbot
│       │   │   └── admin/
│       │   │       ├── dashboard.jsp
│       │   │       ├── login.jsp
│       │   │       ├── content-cards.jsp
│       │   │       ├── testimonials.jsp
│       │   │       ├── gallery.jsp
│       │   │       └── register-admin.jsp
│       │   │
│       │   └── lib/                      # JAR dependencies
│       │       ├── bootstrap-5.3.3.jar
│       │       ├── mysql-connector-j-9.7.0.jar
│       │       └── ... (other JARs)
│       │
│       ├── assets/
│       │   ├── css/
│       │   │   └── main.css              # All styling (1200+ lines)
│       │   └── js/
│       │       └── main.js               # Navigation, chatbot, interactions
│       │
│       └── index.jsp
│
├── target/                               # Compiled output
│   ├── classes/                          # Compiled Java classes
│   ├── pd_webapp/                        # WAR exploded
│   └── pd_webapp.war                     # Deployable WAR file
│
├── pom.xml                               # Maven build configuration
├── README.md
├── SYSTEM_REQUIREMENTS.md                # Requirements documentation
└── SOLUTION_DESIGN_DOCUMENTATION.md      # This file
```

---

## 2.5 Request Flow & Servlet Routing

### HTTP Request Processing
```
CLIENT REQUEST
    │
    ▼
Tomcat Receives HTTP Request
    │
    ▼
URL Mapping (web.xml or annotations)
    │
    ▼
┌─────────────────────────────────────┐
│ Appropriate Servlet Instantiated    │ (if needed)
│ (extends HttpServlet)               │
└─────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────┐
│ AdminAuthFilter (if protected route)│ (checks session)
│ Validates admin authentication      │
└─────────────────────────────────────┘
    │
    ▼
doGet() or doPost() Method Executes
    │
    ├─→ Extract Parameters from Request
    │
    ├─→ Call DAO Methods
    │   └─→ Query/Update Database
    │
    ├─→ Business Logic Processing
    │
    ├─→ Store Results in Request Scope
    │
    └─→ Forward to JSP

JSP Template Renders
    │
    ├─→ Includes common/header.jsp
    ├─→ Renders page content
    ├─→ Includes common/footer.jsp
    │
    ▼
HTML Response
    │
    ▼
CSS & JavaScript Applied
    │
    ▼
Response Sent to Client
```

### Key Servlets & Routes
```
PUBLIC ROUTES (No Authentication Required):
GET  /home                          → HomeServlet
GET  /solutions                     → SolutionsServlet
GET  /solutions/{id}                → SolutionDetailServlet
GET  /cyber-blog                    → BlogServlet
GET  /articles/{id}                 → ArticleDetailServlet
GET  /case-studies                  → CaseStudiesServlet
GET  /case-studies/{id}             → CaseStudyDetailServlet
GET  /gallery                       → GalleryServlet
GET  /events/{id}                   → EventDetailServlet
GET  /testimonials                  → TestimonialsServlet
GET  /contact                       → ContactServlet
POST /submit-security-request       → InquirySubmitServlet
POST /submit-testimonial            → SubmitTestimonialServlet
GET  /thank-you                     → ThankYouServlet

ADMIN ROUTES (Authentication Required / AdminAuthFilter):
GET  /admin/login                   → AdminLoginServlet
POST /admin/login                   → AdminLoginServlet (process)
GET  /admin/logout                  → AdminLogoutServlet
GET  /admin/dashboard               → AdminDashboardServlet
GET  /admin/content-cards           → ManageContentCardsServlet
POST /admin/content-cards/add       → AddContentCardServlet
GET  /admin/content-cards/edit      → EditContentCardServlet
POST /admin/content-cards/update    → UpdateContentCardServlet
POST /admin/content-cards/delete    → DeleteContentCardServlet
GET  /admin/testimonials            → AdminManageTestimonialsServlet
POST /admin/testimonials/approve    → ApproveTestimonialServlet
POST /admin/testimonials/hide       → HideTestimonialServlet
POST /admin/testimonials/delete     → DeleteTestimonialServlet
GET  /admin/gallery                 → AdminGalleryServlet
POST /admin/gallery                 → AdminGalleryServlet (actions)
GET  /admin/gallery/add-images      → AdminAddGalleryImagesServlet
POST /admin/gallery/add-images      → AdminAddGalleryImagesServlet
GET  /admin/register                → AdminRegisterServlet
POST /admin/register                → AdminRegisterServlet (process)
GET  /admin/request                 → AdminRequestDetailServlet
```

---

## 2.6 Security Implementation

### Authentication & Authorization
```
LOGIN FLOW:
1. User submits username/password on /admin/login
2. ServletRequest captured in AdminLoginServlet
3. Password hashed with stored hash comparison
4. On success:
   - HttpSession created
   - Set attribute: "adminUserId" = userId
   - Redirect to /admin/dashboard
5. On failure:
   - Error message set in request scope
   - Forward back to login page

SESSION VALIDATION:
- AdminAuthFilter checks every /admin/* request
- Validates session attribute "adminUserId" exists
- If missing, redirect to /admin/login
- Cookie flags: HttpOnly, Secure (on HTTPS)

LOGOUT:
- AdminLogoutServlet invalidates session
- Clears all session attributes
- Redirects to /admin/login
```

### Input Validation
```
CLIENT-SIDE (JavaScript):
- Required field validation
- Email format checking
- Phone number format
- Numeric range validation (ratings 1-5)
- Character length limits

SERVER-SIDE (Java Servlet):
- Re-validate all inputs (never trust client)
- Type conversion with error handling
- SQL injection prevention via:
  * PreparedStatement (parameterized queries)
  * Input sanitation on string fields
- Email validation (RFC 5322 pattern)
- Phone format validation
- Range validation (votes 1-5, capacity > 0)
- Required field checks

DATABASE CONSTRAINTS:
- UNIQUE constraints on username, email
- CHECK constraints on rating (1-5)
- INT ranges for numeric fields
- ENUM for status/priority fields
- NOT NULL on critical fields
```

### SQL Injection Prevention
```
UNSAFE EXAMPLE:
String sql = "SELECT * FROM client WHERE email = '" + email + "'";
// Vulnerable to: ' OR '1'='1

SAFE EXAMPLE (Used in codebase):
String sql = "SELECT * FROM client WHERE email = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, email);
ResultSet rs = pstmt.executeQuery();
```

---

## 2.7 Data Persistence Layer (DAO Pattern)

### DAO Implementation Pattern
```
DAO PATTERN STRUCTURE:
├── Database Connection Management
├── CRUD Operations (Create, Read, Update, Delete)
├── Result Set Mapping to POJOs
├── Error Handling & Logging
└── Transaction Support

EXAMPLE DAO CLASS:
public class ClientDAO {
    public void saveClient(Client client) {
        // INSERT statement
    }
    
    public Client getClientById(int clientId) {
        // SELECT query
        // Map ResultSet → Client object
    }
    
    public List<Client> getAllClients() {
        // SELECT all clients
        // Map to List<Client>
    }
    
    public void updateClient(Client client) {
        // UPDATE statement
    }
    
    public void deleteClient(int clientId) {
        // DELETE statement
    }
}
```

### Database Connection
```
CONNECTION MANAGEMENT:
- Direct JDBC connection (no framework)
- Connection pooling via DataSource (Tomcat)
- MySQL Connector/J driver loaded dynamically

CONFIG (context.xml):
<Resource name="jdbc/cybernova"
    auth="Container"
    type="javax.sql.DataSource"
    driverClassName="com.mysql.cj.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/cybernova"
    username="admin"
    password="secure_password"
    maxActive="20"
    maxIdle="10"
    maxWait="30000" />

LOOKUP IN SERVLET:
InitialContext ctx = new InitialContext();
DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cybernova");
Connection conn = ds.getConnection();
```

---

## 2.8 Deployment Instructions

### Build Process
```bash
# Full build with compile
mvn clean compile

# Build WAR package
mvn clean package

# Install & run locally
mvn clean install tomcat7:run
```

### Deployment to Tomcat
```bash
# 1. Build WAR file
mvn clean package
# → pd_webapp.war created in target/

# 2. Copy to Tomcat webapps directory
cp target/pd_webapp.war $CATALINA_HOME/webapps/

# 3. Start Tomcat (auto-deploys)
$CATALINA_HOME/bin/startup.sh

# 4. Access application
http://localhost:8080/pd_webapp/
```

### Production Configuration
```
TOMCAT TUNING:
- Increase heap memory: CATALINA_OPTS="-Xmx1024m -Xms512m"
- Connection pool optimization
- Enable HTTPS (SSL/TLS certificates)
- Set security manager
- Configure logging levels
- Enable compression for static assets

DATABASE BACKUP:
- Daily incremental backups
- Weekly full backups
- Test recovery procedures
- Store offsite

MONITORING:
- Application logs (catalina.out)
- Database query logs
- JVM memory usage
- Connection pool utilization
- Response time metrics
```

---

## 2.9 Development Guidelines

### Code Standards
```
NAMING CONVENTIONS:
- Classes: PascalCase (ClientDAO)
- Methods: camelCase (getClientById)
- Variables: camelCase (clientId)
- Constants: UPPER_SNAKE_CASE (MAX_RETRIES)
- Tables: snake_case (admin_user, security_request)
- Columns: snake_case (user_id, created_at)

FILE ORGANIZATION:
- Servlet per major feature
- DAO per entity/table
- Model class per entity
- Shared utilities in util package

DOCUMENTATION:
- Javadoc on public methods
- Inline comments for complex logic
- README for setup instructions
- This design document for architecture
```

### Testing Strategy
```
UNIT TESTS:
- Test DAO methods in isolation
- Mock database connections
- Test business logic
- Test validation logic

INTEGRATION TESTS:
- Test Servlet request/response cycle
- Test database operations end-to-end
- Test authentication flow

MANUAL TESTING:
- Browser testing (Chrome, Firefox, Safari, Edge)
- Mobile testing (iPhone, Android)
- Admin functionality testing
- Security testing (SQL injection attempts)
```

---

## 2.10 Performance Optimization

### Caching Strategy
```
ASSET CACHING:
- CSS, JavaScript, Images: 1-year cache with versioning
- HTML: No-cache (always fetch fresh)
- API responses: Cache per business need

QUERY OPTIMIZATION:
- Indexes on frequently filtered columns (status, created_at)
- Avoid N+1 queries (fetch related data in single query)
- Use LIMIT for pagination
- Select only needed columns

FRONTEND OPTIMIZATION:
- Minify CSS and JavaScript
- Lazy-load images
- Responsive images with srcset
- Reduce HTTP requests (combine files)
- Enable gzip compression
```

### Load Optimization
```
PAGINATION:
- Limit 20-50 records per page
- Client can navigate pages
- Reduces memory usage
- Improves response time

CONNECTION POOLING:
- Min connections: 5
- Max connections: 20
- Max wait time: 30 seconds
- Reuse connections instead of creating new ones
```

---

## 2.11 Future Enhancements

### Planned Features
```
PHASE 2:
- User profile management
- Email notifications
- Two-factor authentication
- Advanced reporting/analytics

PHASE 3:
- Mobile application (native iOS/Android)
- REST API for integrations
- Third-party integrations (Slack, Teams)
- Machine learning for threat prediction

PHASE 4:
- Microservices architecture migration
- Kubernetes deployment
- Advanced security scanning
- Automated compliance reporting
```

---

# SUMMARY

This Solution Design Documentation covers:

✅ Comprehensive Prototype Design (wireframes, components, styles, animations)  
✅ Complete System Architecture (layers, stack, deployment)  
✅ Database Schema Design (ERD, table definitions)  
✅ Project Structure Organization  
✅ Request Flow & Servlet Routing  
✅ Security Implementation Details  
✅ Data Persistence (DAO Pattern)  
✅ Deployment Instructions  
✅ Development Guidelines  
✅ Performance Optimization Strategies  

---

*Document prepared for CyberNova Analytics Ltd development team*  
*Last updated: May 11, 2026*  
*Version: 1.0*

