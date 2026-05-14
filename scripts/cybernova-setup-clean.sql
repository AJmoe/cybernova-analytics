-- CyberNova Analytics Ltd - Database Schema
-- Cybersecurity Service Management System
-- Created for Computer Systems Engineering Project

DROP DATABASE IF EXISTS cybernova_db;
CREATE DATABASE cybernova_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cybernova_db;

CREATE TABLE admin_users (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    full_name VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE clients (
    client_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    organization VARCHAR(200),
    country VARCHAR(100),
    job_title VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE security_requests (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    type_of_security_issue VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('NEW', 'IN_PROGRESS', 'RESOLVED', 'CLOSED') DEFAULT 'NEW',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    region VARCHAR(100),
    assigned_to INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at DATETIME,
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES admin_users(admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE technical_problems (
    problem_id INT PRIMARY KEY AUTO_INCREMENT,
    problem_name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    category VARCHAR(100) NOT NULL,
    severity_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cyber_blog_articles (
    article_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    author VARCHAR(150),
    content LONGTEXT NOT NULL,
    summary VARCHAR(500),
    category VARCHAR(100),
    tags VARCHAR(300),
    published_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT TRUE,
    view_count INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE case_studies (
    case_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    client_organization VARCHAR(200),
    challenge TEXT NOT NULL,
    solution TEXT NOT NULL,
    results TEXT NOT NULL,
    industry VARCHAR(100),
    published_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE testimonials (
    testimonial_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(250),
    feedback_text TEXT NOT NULL,
    service_type VARCHAR(100),
    published_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE workshop_events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    description TEXT NOT NULL,
    event_date DATETIME NOT NULL,
    location VARCHAR(200),
    capacity INT,
    registered_count INT DEFAULT 0,
    instructor VARCHAR(150),
    event_type VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE workshop_gallery (
    gallery_id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    image_title VARCHAR(250),
    image_description TEXT,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    display_order INT,
    FOREIGN KEY (event_id) REFERENCES workshop_events(event_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE solutions (
    solution_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(100),
    features TEXT,
    benefits TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE request_analytics (
    analytics_id INT PRIMARY KEY AUTO_INCREMENT,
    request_id INT NOT NULL,
    service_type VARCHAR(100) NOT NULL,
    region VARCHAR(100),
    country VARCHAR(100),
    request_count INT DEFAULT 1,
    resolution_time_hours INT,
    client_satisfaction_rating INT,
    recorded_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (request_id) REFERENCES security_requests(request_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE request_type_analytics (
    analytics_id INT PRIMARY KEY AUTO_INCREMENT,
    request_type VARCHAR(100) NOT NULL,
    total_requests INT DEFAULT 0,
    resolved_count INT DEFAULT 0,
    pending_count INT DEFAULT 0,
    average_resolution_time_hours DECIMAL(10, 2),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE regional_demand (
    demand_id INT PRIMARY KEY AUTO_INCREMENT,
    region VARCHAR(100) NOT NULL,
    country VARCHAR(100),
    service_type VARCHAR(100),
    request_count INT DEFAULT 0,
    demand_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'LOW',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_security_requests_client ON security_requests(client_id);
CREATE INDEX idx_security_requests_status ON security_requests(status);
CREATE INDEX idx_security_requests_type ON security_requests(type_of_security_issue);
CREATE INDEX idx_security_requests_region ON security_requests(region);
CREATE INDEX idx_security_requests_created ON security_requests(created_at);

CREATE INDEX idx_testimonials_client ON testimonials(client_id);
CREATE INDEX idx_testimonials_published ON testimonials(is_published);
CREATE INDEX idx_testimonials_rating ON testimonials(rating);

CREATE INDEX idx_blog_articles_published ON cyber_blog_articles(is_published);
CREATE INDEX idx_blog_articles_category ON cyber_blog_articles(category);
CREATE INDEX idx_blog_articles_published_date ON cyber_blog_articles(published_date);

CREATE INDEX idx_case_studies_published ON case_studies(is_published);
CREATE INDEX idx_case_studies_industry ON case_studies(industry);

CREATE INDEX idx_workshop_events_date ON workshop_events(event_date);
CREATE INDEX idx_workshop_events_active ON workshop_events(is_active);

CREATE INDEX idx_request_analytics_date ON request_analytics(recorded_date);
CREATE INDEX idx_request_analytics_region ON request_analytics(region);
CREATE INDEX idx_request_analytics_service ON request_analytics(service_type);

CREATE INDEX idx_regional_demand_region ON regional_demand(region);
CREATE INDEX idx_regional_demand_country ON regional_demand(country);

INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES ('admin', '0192023a7bbd73250516f069df18b500', 'admin@cybernova.com', 'System Administrator', TRUE);

INSERT INTO technical_problems (problem_name, description, category, severity_level)
VALUES
('Ransomware Detection', 'Detection and response to ransomware threats', 'Threat Detection', 'CRITICAL'),
('DDoS Protection', 'Distributed Denial of Service attack mitigation', 'Network Security', 'HIGH'),
('Vulnerability Assessment', 'Comprehensive security vulnerability scanning', 'Assessment', 'MEDIUM'),
('Malware Removal', 'Detection and removal of malware and spyware', 'Threat Removal', 'HIGH'),
('Security Audit', 'Full security infrastructure audit', 'Audit', 'MEDIUM');

INSERT INTO solutions (title, description, category, features, benefits, is_published)
VALUES
('Advanced Threat Protection', 'Real-time threat detection and prevention', 'Threat Management', 'Real-time monitoring, AI-powered detection', 'Reduced breach time, 24/7 protection', TRUE),
('Incident Response', 'Rapid incident detection and response', 'Incident Management', 'Quick response, Expert analysts', 'Minimized damage, Fast recovery', TRUE),
('Compliance Management', 'Ensure regulatory compliance', 'Compliance', 'Automated checks, Reporting', 'Regulatory adherence, Risk reduction', TRUE);

INSERT INTO case_studies (title, client_organization, challenge, solution, results, industry, is_published)
VALUES
('Banking Security Enhancement', 'Major Financial Institution', 'Vulnerable to insider threats and data breaches', 'Implemented multi-layer security with behavioral analytics', '99.9% threat detection rate, Zero breaches in 12 months', 'Financial Services', TRUE),
('Healthcare Data Protection', 'Regional Hospital Network', 'Patient data privacy concerns and compliance issues', 'Deployed HIPAA-compliant security infrastructure', 'Full HIPAA compliance, Patient trust increased', 'Healthcare', TRUE);

CREATE VIEW vw_security_request_summary AS
SELECT
    sr.status,
    sr.type_of_security_issue,
    COUNT(*) as total_requests,
    AVG(DATEDIFF(sr.resolved_at, sr.created_at)) as avg_resolution_days
FROM security_requests sr
GROUP BY sr.status, sr.type_of_security_issue;

CREATE VIEW vw_regional_demand_summary AS
SELECT
    rd.country,
    rd.region,
    rd.service_type,
    SUM(rd.request_count) as total_requests,
    rd.demand_level
FROM regional_demand rd
GROUP BY rd.country, rd.region, rd.service_type;

CREATE VIEW vw_testimonial_average_rating AS
SELECT
    service_type,
    ROUND(AVG(rating), 2) as average_rating,
    COUNT(*) as total_testimonials
FROM testimonials
WHERE is_published = TRUE
GROUP BY service_type;

