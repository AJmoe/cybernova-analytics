package com.taskproject.pd_webapp.model;

import java.time.LocalDateTime;

/**
 * Model class for Clients
 * Represents client/organization information for security requests
 */
public class Client {
    private int clientId;
    private String name;
    private String email;
    private String phone;
    private String organization;
    private String country;
    private String jobTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Client() {
    }

    public Client(String name, String email, String phone, String organization, 
                 String country, String jobTitle) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.organization = organization;
        this.country = country;
        this.jobTitle = jobTitle;
    }

    // Getters and Setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
}

