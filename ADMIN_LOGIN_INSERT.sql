-- ADMIN LOGIN FIX - SQL INSERT COMMAND
-- Created: May 6, 2026
-- Database: cybernova_db
-- Table: admin_users

-- ============================================================================
-- OPTION 1: Insert new admin user (if table is empty)
-- ============================================================================

INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES (
    'admin',
    'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=',
    'admin@cybernova.com',
    'System Administrator',
    TRUE
);

-- ============================================================================
-- OPTION 2: Replace existing admin user (DELETE then INSERT)
-- ============================================================================

DELETE FROM admin_users WHERE username = 'admin';

INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES (
    'admin',
    'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=',
    'admin@cybernova.com',
    'System Administrator',
    TRUE
);

-- ============================================================================
-- OPTION 3: Update existing admin user password only
-- ============================================================================

UPDATE admin_users
SET password_hash = 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE='
WHERE username = 'admin';

-- ============================================================================
-- VERIFY THE INSERT
-- ============================================================================

SELECT admin_id, username, email, full_name, is_active, created_at
FROM admin_users
WHERE username = 'admin';

-- ============================================================================
-- CREDENTIALS FOR TESTING
-- ============================================================================
-- Username: admin
-- Password: admin123
-- Login URL: http://localhost:8080/pd_webapp/admin/login

-- ============================================================================
-- NOTE: Password Hash Format
-- ============================================================================
-- Format: [Base64_Salt]$[Base64_Hash]
-- Algorithm: SHA-256 with 16-byte random salt
-- This hash was generated from password: admin123
--
-- The password verification is handled by:
-- File: src/main/java/com/taskproject/pd_webapp/dao/AdminUserDAO.java
-- Method: authenticateAdmin(String username, String plainPassword)

