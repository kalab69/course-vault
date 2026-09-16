package com.example.coursevault.dto;

public class CourseResponse {
    private int id;
    private String courseName;
    private String code;

    public CourseResponse(String code, String courseName, int id) {
        this.code = code;
        this.courseName = courseName;
        this.id = id;
    }
}
