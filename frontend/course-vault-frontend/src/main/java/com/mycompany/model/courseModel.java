/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author Abreham
 */
public class courseModel {

    public enum YearLevel {
        FIRST,
        SECOND,
        THIRD,
        FOURTH
    }
    private int id;
    private String code;
    private String courseName;
    private YearLevel yearLevel;

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCode() {
        return code;
    }

    public YearLevel getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(YearLevel yearLevel) {
        this.yearLevel = yearLevel;
    }
}
