package com.example.coursevault.dto;

import com.example.coursevault.model.ResourceType;

public class CourseResourceResponse {
    private int id;
    private String title;
    private int courseId;
    private ResourceType type;
    private String downloadUrl;

    public CourseResourceResponse(int id, String title, int courseId, ResourceType type, String downloadUrl) {
        this.id = id;
        this.title = title;
        this.courseId = courseId;
        this.type = type;
        this.downloadUrl = downloadUrl;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getCourseId() { return courseId; }
    public ResourceType getType() { return type; }
    public String getDownloadUrl() { return downloadUrl; }
}