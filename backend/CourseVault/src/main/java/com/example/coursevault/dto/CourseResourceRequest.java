package com.example.coursevault.dto;

import com.example.coursevault.model.Course;
import com.example.coursevault.model.ResourceType;

public class CourseResourceRequest {
    private String title;
    private Course course;
    private ResourceType type;
    private String filePath;

    public CourseResourceRequest(Course course, String filePath, String title, ResourceType type) {
        this.course = course;
        this.filePath = filePath;
        this.title = title;
        this.type = type;
    }

    public CourseResourceRequest() {
    }

    public Course getCourse() {
        return course;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public ResourceType getType() {
        return type;
    }
}
