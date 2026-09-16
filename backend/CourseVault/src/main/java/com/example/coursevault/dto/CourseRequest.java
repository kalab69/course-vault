package com.example.coursevault.dto;

public class CourseRequest {
    private String courseName;
    private String code;

    public CourseRequest() {
    }

    public CourseRequest(String code, String courseName) {
        this.code = code;
        this.courseName = courseName;
    }

    public String getCode() {
        return code;
    }

    public String getCourseName() {
        return courseName;
    }
}
