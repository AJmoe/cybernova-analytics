package com.taskproject.pd_webapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestAnalytics {
    private long totalRequests;
    private String topIssueType;
    private String topCountry;
    private Map<String, Long> issueTypeCounts = new LinkedHashMap<>();
    private Map<String, Long> countryCounts = new LinkedHashMap<>();

    public long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public String getTopIssueType() {
        return topIssueType;
    }

    public void setTopIssueType(String topIssueType) {
        this.topIssueType = topIssueType;
    }

    public String getTopCountry() {
        return topCountry;
    }

    public void setTopCountry(String topCountry) {
        this.topCountry = topCountry;
    }

    public Map<String, Long> getIssueTypeCounts() {
        return issueTypeCounts;
    }

    public void setIssueTypeCounts(Map<String, Long> issueTypeCounts) {
        this.issueTypeCounts = issueTypeCounts;
    }

    public Map<String, Long> getCountryCounts() {
        return countryCounts;
    }

    public void setCountryCounts(Map<String, Long> countryCounts) {
        this.countryCounts = countryCounts;
    }
}

