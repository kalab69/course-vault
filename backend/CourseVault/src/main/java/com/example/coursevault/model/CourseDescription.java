package com.example.coursevault.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_description")
public class CourseDescription {

    @Id
    private int courseId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    public CourseDescription() {
    }

    public CourseDescription(int courseId, String description) {
        this.courseId = courseId;
        this.description = description;
        this.generatedAt = LocalDateTime.now();
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
}