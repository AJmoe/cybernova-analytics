package com.taskproject.pd_webapp.model;

public class CyberBlogArticle {
    private String title;
    private String summary;
    private String category;
    private String author;

    public CyberBlogArticle() {
    }

    public CyberBlogArticle(String title, String summary, String category, String author) {
        this.title = title;
        this.summary = summary;
        this.category = category;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

