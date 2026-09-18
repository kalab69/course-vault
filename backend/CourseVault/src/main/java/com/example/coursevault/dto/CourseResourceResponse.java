package com.example.coursevault.dto;

import com.example.coursevault.model.Course;
import com.example.coursevault.model.ResourceType;

public class CourseResourceResponse {
    private int id;
    private String title;
    private Course course;
    private ResourceType type;
    private String filePath;
    public CourseResourceResponse(int id,Course course, String filePath, String title, ResourceType type) {
        this.course = course;
        this.filePath = filePath;
        this.title = title;
        this.type = type;
        this.id = id;
    }

    public CourseResourceResponse() {
    }
}
