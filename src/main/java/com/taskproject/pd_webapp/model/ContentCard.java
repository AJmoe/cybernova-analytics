package com.taskproject.pd_webapp.model;

import java.sql.Timestamp;

public class ContentCard {
    private int cardId;
    private String pageType;
    private String title;
    private String description;
    private String imageUrl;
    private String externalLink;
    private String status;
    private Timestamp createdAt;

    public ContentCard() {
    }

    public ContentCard(int cardId, String pageType, String title, String description,
                       String imageUrl, String externalLink, String status, Timestamp createdAt) {
        this.cardId = cardId;
        this.pageType = pageType;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.externalLink = externalLink;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}