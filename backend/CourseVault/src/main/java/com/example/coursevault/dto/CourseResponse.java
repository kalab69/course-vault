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
