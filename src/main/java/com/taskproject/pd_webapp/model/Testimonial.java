package com.taskproject.pd_webapp.model;

import java.sql.Timestamp;

public class Testimonial {

    private int testimonialId;
    private int clientId;
    private int rating;
    private String title;
    private String feedbackText;
    private String serviceType;
    private Timestamp publishedDate;
    private boolean isPublished;

    // Optional (for display)
    private String clientName;
    private String organization;

    public Testimonial() {
    }

    // ===== Getters & Setters =====

    public int getTestimonialId() {
        return testimonialId;
    }

    public void setTestimonialId(int testimonialId) {
        this.testimonialId = testimonialId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Timestamp getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Timestamp publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}