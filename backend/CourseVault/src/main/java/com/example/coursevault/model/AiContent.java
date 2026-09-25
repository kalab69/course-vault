package com.example.coursevault.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_content")
public class AiContent {

    @Id
    private int resourceId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    public AiContent() {
    }

    public AiContent(int resourceId, String summary) {
        this.resourceId = resourceId;
        this.summary = summary;
        this.generatedAt = LocalDateTime.now();
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
}