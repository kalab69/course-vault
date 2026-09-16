package com.example.coursevault.model;

import jakarta.persistence.*;

@Entity
@Table
public class ExternalLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String topic;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    public ExternalLink() {
    }

    public ExternalLink(Course course, int id, String title, String topic, String url) {
        this.course = course;
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.url = url;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
