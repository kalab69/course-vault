package com.example.coursevault.dto;

import com.example.coursevault.model.Course;

public class ExternalLinkResponse {
    private int id;
    private Course course;
    private String topic;
    private String title;
    private String url;

    public ExternalLinkResponse(int id,Course course, String title, String topic, String url) {
        this.course = course;
        this.title = title;
        this.topic = topic;
        this.url = url;
        this.id =id;
    }

    public ExternalLinkResponse() {
    }

    public Course getCourse() {
        return course;
    }

    public int getId() {
        return id;
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
