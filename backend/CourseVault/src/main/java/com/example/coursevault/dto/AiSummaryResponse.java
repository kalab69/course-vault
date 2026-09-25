package com.example.coursevault.dto;

import java.time.LocalDateTime;

public class AiSummaryResponse {

    private int resourceId;
    private String summary;
    private LocalDateTime generatedAt;
    private boolean cached;

    public AiSummaryResponse() {
    }

    public AiSummaryResponse(int resourceId, String summary, LocalDateTime generatedAt, boolean cached) {
        this.resourceId = resourceId;
        this.summary = summary;
        this.generatedAt = generatedAt;
        this.cached = cached;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }
}