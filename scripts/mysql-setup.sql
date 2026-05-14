-- CyberNova Analytics Ltd - Complete Database Schema v2.0
-- New Requirements Implementation
-- Created: April 29, 2026
-- Password: PASSWORD@123

-- ============================================================================
-- CREATE DATABASE
-- ============================================================================

DROP DATABASE IF EXISTS cybernova_analytics;
CREATE DATABASE cybernova_analytics
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE cybernova_analytics;

-- ============================================================================
-- TABLE 1: ADMIN USERS (Authentication & Access Control)
-- ============================================================================

CREATE TABLE admin_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(200) NOT NULL,
    full_name VARCHAR(150),
    role ENUM('admin', 'manager', 'analyst') DEFAULT 'analyst',
    is_active BOOLEAN DEFAULT TRUE,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 2: SECURITY REQUESTS (Core - Client Security Inquiries)
-- ============================================================================

CREATE TABLE security_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Client Information
    name VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    organization VARCHAR(200) NOT NULL,
    country VARCHAR(120) NOT NULL,
    job_title VARCHAR(150) NOT NULL,

    -- Request Details
    issue_type VARCHAR(150) NOT NULL COMMENT 'Phishing Response, Incident Response, Vulnerability Assessment, Cloud Hardening, Security Awareness Training, Compliance Review',
    description LONGTEXT NOT NULL,
    severity ENUM('low', 'medium', 'high', 'critical') DEFAULT 'medium',

    -- Status & Workflow
    status VARCHAR(50) NOT NULL DEFAULT 'New' COMMENT 'New, In Review, Action Required, In Progress, Closed, Escalated',
    assigned_to BIGINT,
    priority INT DEFAULT 5 COMMENT '1=Highest, 10=Lowest',

    -- Tracking
    budget_range VARCHAR(50),
    timeline VARCHAR(100),
    additional_details LONGTEXT,

    -- Audit Trail
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    viewed_at TIMESTAMP NULL,

    INDEX idx_status (status),
    INDEX idx_issue_type (issue_type),
    INDEX idx_country (country),
    INDEX idx_severity (severity),
    INDEX idx_assigned_to (assigned_to),
    INDEX idx_created_at (created_at),
    INDEX idx_organization (organization),
    FOREIGN KEY (assigned_to) REFERENCES admin_users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 3: REQUEST NOTES & COMMUNICATIONS
-- ============================================================================

CREATE TABLE request_notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    note_type ENUM('internal', 'client_update', 'status_change') DEFAULT 'internal',
    content LONGTEXT NOT NULL,
    is_internal BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_request_id (request_id),
    INDEX idx_created_by (created_by),
    INDEX idx_created_at (created_at),
    FOREIGN KEY (request_id) REFERENCES security_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES admin_users(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 4: REQUEST STATUS HISTORY (Audit Trail)
-- ============================================================================

CREATE TABLE request_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id BIGINT NOT NULL,
    old_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,
    changed_by BIGINT NOT NULL,
    reason VARCHAR(500),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_request_id (request_id),
    INDEX idx_changed_at (changed_at),
    FOREIGN KEY (request_id) REFERENCES security_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (changed_by) REFERENCES admin_users(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 5: CASE STUDIES (Marketing & Proof of Concept)
-- ============================================================================

CREATE TABLE IF NOT EXISTS case_studies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    solution VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 6: CYBER BLOG ARTICLES (Content Marketing)
-- ============================================================================

CREATE TABLE IF NOT EXISTS cyber_blog_articles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    author VARCHAR(150),
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 7: TESTIMONIALS (Client Social Proof)
-- ============================================================================

CREATE TABLE IF NOT EXISTS testimonials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(150) NOT NULL,
    author_title VARCHAR(150),
    company VARCHAR(200),
    testimonial_text LONGTEXT NOT NULL,
    rating INT DEFAULT 5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE 8: WORKSHOP & EVENT GALLERY
-- ============================================================================

CREATE TABLE IF NOT EXISTS workshop_gallery (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    image_url VARCHAR(500),
    event_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- INSERT DEFAULT DATA
-- ============================================================================

INSERT INTO admin_users (username, password_hash, email, full_name, role, is_active)
VALUES ('admin', 'PASSWORD@123', 'admin@cybernova.com', 'CyberNova Administrator', 'admin', TRUE);

INSERT INTO security_requests (name, email, phone, organization, country, job_title, issue_type, description, severity, status, priority) VALUES
('Amina Moyo', 'amina@northwave.bw', '+267 71234567', 'NorthWave Bank', 'Botswana', 'Risk Officer', 'Phishing Response', 'Urgent phishing campaign targeting staff mailbox access. Multiple employees compromised. Need immediate response.', 'critical', 'In Review', 1),
('Tebogo Mhlongo', 'tebogo@metrocare.co.bw', '+267 72123456', 'MetroCare Clinics', 'Botswana', 'IT Manager', 'Vulnerability Assessment', 'Need comprehensive technical scan for exposed services and patch priorities for healthcare infrastructure.', 'high', 'New', 2),
('Sipho Dlamini', 'sipho@logisafrica.za', '+27 821234567', 'Logis Africa', 'South Africa', 'Security Lead', 'Incident Response', 'Suspicious lateral movement detected on warehouse endpoints. Possible ransomware. Need forensic analysis.', 'critical', 'In Review', 1),
('Naledi Mahlangu', 'naledi@financehub.na', '+264 811234567', 'Finance Hub Namibia', 'Namibia', 'Compliance Manager', 'Security Awareness Training', 'Staff training program and policy review following compliance audit findings on access control.', 'medium', 'New', 5),
('Kabelo Mabuza', 'kabelo@publicworks.gov.bw', '+267 75432100', 'Ministry Project Office', 'Botswana', 'Director', 'Cloud Hardening', 'Review of AWS cloud account controls, IAM policies, and privileged access management configuration.', 'high', 'New', 2);

-- Create case_studies table
CREATE TABLE IF NOT EXISTS case_studies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    solution VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create cyber_blog_articles table
CREATE TABLE IF NOT EXISTS cyber_blog_articles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    author VARCHAR(150),
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create testimonials table
CREATE TABLE IF NOT EXISTS testimonials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(150) NOT NULL,
    author_title VARCHAR(150),
    company VARCHAR(200),
    testimonial_text LONGTEXT NOT NULL,
    rating INT DEFAULT 5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create workshop_gallery table
CREATE TABLE IF NOT EXISTS workshop_gallery (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    image_url VARCHAR(500),
    event_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample admin user (password: admin123 - should be hashed in production)
INSERT INTO admin_users (username, password_hash, email)
VALUES ('admin', 'admin123', 'admin@cybernova.com')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- Insert sample security requests
INSERT INTO security_requests (name, email, phone, organization, country, job_title, issue_type, description, status)
VALUES
('Amina', 'amina@northwave.bw', '+267 71234567', 'NorthWave Bank', 'Botswana', 'Risk Officer', 'Phishing Response', 'Urgent phishing campaign targeting staff mailbox access.', 'New'),
('Tebogo', 'tebogo@metrocare.co.bw', '+267 72123456', 'MetroCare Clinics', 'Botswana', 'IT Manager', 'Vulnerability Assessment', 'Need a technical scan for exposed services and patch priorities.', 'New'),
('Sipho', 'sipho@logisafrica.za', '+27 821234567', 'Logis Africa', 'South Africa', 'Security Lead', 'Incident Response', 'Suspicious lateral movement detected on warehouse endpoints.', 'In Review'),
('Naledi', 'naledi@financehub.na', '+264 811234567', 'Finance Hub Namibia', 'Namibia', 'Compliance Manager', 'Security Awareness Training', 'Requesting staff training plus policy review after recent audit findings.', 'New'),
('Kabelo', 'kabelo@publicworks.gov.bw', '+267 75432100', 'Ministry Project Office', 'Botswana', 'Director', 'Cloud Hardening', 'Need review of cloud account controls and privileged access configuration.', 'New')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

