package com.example.coursevault.dto;

public class ExternalLinkResponse {
    private int id;
    private int courseId;
    private String topic;
    private String title;
    private String url;

    public ExternalLinkResponse(int id, int courseId, String title, String topic, String url) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.topic = topic;
        this.url = url;
    }

    public ExternalLinkResponse() {}

    public int getId() { return id; }
    public int getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getTopic() { return topic; }
    public String getUrl() { return url; }
}