package com.taskproject.pd_webapp.service;

import com.taskproject.pd_webapp.model.RequestAnalytics;
import com.taskproject.pd_webapp.model.SecurityRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RequestAnalyticsService {
    public RequestAnalytics buildAnalytics(List<SecurityRequest> requests) {
        RequestAnalytics analytics = new RequestAnalytics();
        analytics.setTotalRequests(requests.size());
        analytics.setIssueTypeCounts(countBy(requests, SecurityRequest::getTypeOfSecurityIssue));
        analytics.setCountryCounts(countBy(requests, SecurityRequest::getCountry));
        analytics.setTopIssueType(topKey(analytics.getIssueTypeCounts()));
        analytics.setTopCountry(topKey(analytics.getCountryCounts()));
        return analytics;
    }

    private Map<String, Long> countBy(List<SecurityRequest> requests, java.util.function.Function<SecurityRequest, String> extractor) {
        return requests.stream()
                .filter(request -> extractor.apply(request) != null && !extractor.apply(request).isBlank())
                .collect(Collectors.groupingBy(extractor, LinkedHashMap::new, Collectors.counting()));
    }

    private String topKey(Map<String, Long> counts) {
        return counts.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse("N/A");
    }
}

