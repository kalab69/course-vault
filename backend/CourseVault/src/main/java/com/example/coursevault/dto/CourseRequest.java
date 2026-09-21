package com.example.coursevault.dto;

import com.example.coursevault.model.YearLevel;

public class CourseRequest {
    private String courseName;
    private String code;
    private YearLevel yearLevel;
    public CourseRequest() {
    }

    public CourseRequest(String code, String courseName , YearLevel yearLevel) {
        this.code = code;
        this.courseName = courseName;
        this.yearLevel = yearLevel;
    }

    public String getCode() {
        return code;
    }

    public String getCourseName() {
        return courseName;
    }

    public YearLevel getYearLevel() {
        return yearLevel;
    }
}
