package com.example.coursevault.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String courseName;
    @Column(nullable = false, unique = true)
    private String code;

    public Course() {
    }

    public Course(String code, String courseName, int id) {
        this.code = code;
        this.courseName = courseName;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
