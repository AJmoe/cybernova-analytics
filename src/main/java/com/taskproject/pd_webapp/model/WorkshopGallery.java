package com.taskproject.pd_webapp.model;

public class WorkshopGallery {
    private String title;
    private String description;
    private String eventDate;
    private String location;
    // Comma-separated image URLs for the event. Allows multiple images per event.
    private String imageUrls;

    public WorkshopGallery() {
    }

    public WorkshopGallery(String title, String description, String eventDate, String location) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

