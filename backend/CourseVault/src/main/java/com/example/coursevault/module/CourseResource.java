package com.example.coursevault.module;

import jakarta.persistence.*;

@Entity
@Table(name = "Course_resources")
public class CourseResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Column(nullable = false)
    private String filePath;

    public CourseResource(Course course, String filePath, int id, String title, ResourceType type) {
        this.course = course;
        this.filePath = filePath;
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public CourseResource() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }
}
