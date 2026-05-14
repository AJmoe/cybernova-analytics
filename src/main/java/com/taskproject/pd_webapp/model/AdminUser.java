package com.taskproject.pd_webapp.model;

import java.time.LocalDateTime;

/**
 * Model class for Admin Users
 * Represents administrator accounts for CyberNova Analytics dashboard
 */
public class AdminUser {
    private int adminId;
    private String username;
    private String passwordHash;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    public AdminUser() {
    }

    public AdminUser(String username, String passwordHash, String email, String fullName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fullName = fullName;
        this.isActive = true;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

