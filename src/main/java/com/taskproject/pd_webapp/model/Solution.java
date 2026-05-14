package com.taskproject.pd_webapp.model;

import java.time.LocalDateTime;

/**
 * Model class for Cybersecurity Solutions
 * Represents detailed cybersecurity solutions offered by CyberNova Analytics
 */
public class Solution {
    private int solutionId;
    private String title;
    private String description;
    private String category;
    private String features;
    private String benefits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isPublished;

    public Solution() {
    }

    public Solution(String title, String description, String category, 
                   String features, String benefits) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.features = features;
        this.benefits = benefits;
        this.isPublished = true;
    }

    // Getters and Setters
    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
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

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}

