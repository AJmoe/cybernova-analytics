package com.taskproject.pd_webapp.model;

import java.time.LocalDateTime;

/**
 * Model class for Regional Demand Analytics
 * Tracks regional cybersecurity service demand
 */
public class RegionalDemand {
    private int demandId;
    private String region;
    private String country;
    private String serviceType;
    private int requestCount;
    private String demandLevel; // LOW, MEDIUM, HIGH, CRITICAL
    private LocalDateTime lastUpdated;

    public RegionalDemand() {
    }

    public RegionalDemand(String region, String country, String serviceType, 
                         int requestCount, String demandLevel) {
        this.region = region;
        this.country = country;
        this.serviceType = serviceType;
        this.requestCount = requestCount;
        this.demandLevel = demandLevel;
    }

    // Getters and Setters
    public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

