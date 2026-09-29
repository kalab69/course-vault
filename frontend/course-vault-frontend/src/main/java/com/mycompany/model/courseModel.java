package com.mycompany.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class courseModel {

    public enum YearLevel {
        FIRST, SECOND, THIRD, FOURTH
    }

    private int id;
    private String code;
    private String courseName;
    private YearLevel yearLevel; 

    public courseModel() {}

    public int getId()              { return id; }
    public String getCode()         { return code; }
    public String getCourseName()   { return courseName; }
    public YearLevel getYearLevel() { return yearLevel; }

    // Setters for Jackson
    public void setId(int id)                       { this.id = id; }
    public void setCode(String code)                { this.code = code; }
    public void setCourseName(String name)          { this.courseName = name; }
    public void setYearLevel(YearLevel yearLevel)   { this.yearLevel = yearLevel; }
}