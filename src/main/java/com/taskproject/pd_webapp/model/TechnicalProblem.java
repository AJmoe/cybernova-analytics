package com.taskproject.pd_webapp.model;

public class TechnicalProblem {
    private String summary;
    private String severity;

    public TechnicalProblem() {
    }

    public TechnicalProblem(String summary, String severity) {
        this.summary = summary;
        this.severity = severity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}

