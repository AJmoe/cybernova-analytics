package com.taskproject.pd_webapp.model;

import java.time.LocalDateTime;

/**
 * Model class for Workshop Events
 * Represents cybersecurity training workshops and events
 */
public class WorkshopEvent {
    private int eventId;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String location;
    private int capacity;
    private int registeredCount;
    private String instructor;
    private String eventType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    public WorkshopEvent() {
    }

    public WorkshopEvent(String title, String description, LocalDateTime eventDate, 
                        String location, int capacity, String instructor, String eventType) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.capacity = capacity;
        this.instructor = instructor;
        this.eventType = eventType;
        this.registeredCount = 0;
        this.isActive = true;
    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

