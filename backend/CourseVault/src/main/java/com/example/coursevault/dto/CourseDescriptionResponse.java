package com.example.coursevault.dto;

import java.time.LocalDateTime;

public class CourseDescriptionResponse {

    private int courseId;
    private String description;
    private LocalDateTime generatedAt;
    private boolean cached;

    public CourseDescriptionResponse() {
    }

    public CourseDescriptionResponse(int courseId, String description, LocalDateTime generatedAt, boolean cached) {
        this.courseId = courseId;
        this.description = description;
        this.generatedAt = generatedAt;
        this.cached = cached;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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