package com.example.coursevault.dto;

import com.example.coursevault.model.Course;

public class ExternalLinkRequest {
    private Course course;
    private String topic;
    private String title;
    private String url;

    public ExternalLinkRequest(Course course, String title, String topic, String url) {
        this.course = course;
        this.title = title;
        this.topic = topic;
        this.url = url;
    }

    public ExternalLinkRequest() {
    }

    public Course getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }
}
