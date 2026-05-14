package com.taskproject.pd_webapp.model;

public class CaseStudy {
    private String title;
    private String challenge;
    private String outcome;

    public CaseStudy() {
    }

    public CaseStudy(String title, String challenge, String outcome) {
        this.title = title;
        this.challenge = challenge;
        this.outcome = outcome;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}

