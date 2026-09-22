package com.example.coursevault.controller;

import com.example.coursevault.dto.CourseRequest;
import com.example.coursevault.dto.CourseResponse;
import com.example.coursevault.model.Course;
import com.example.coursevault.model.YearLevel;
import com.example.coursevault.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    CourseService courseService;

    @Autowired
    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/api/courses")
    public ResponseEntity<List<CourseResponse>> listCourses(@RequestParam(required = false) YearLevel year) {
        List<Course> courses = (year == null)
                ? courseService.listCourses()
                : courseService.filterCourseByYear(year);
        List<CourseResponse> courseResponse = courses.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable int id) {
        Course course = courseService.findCourse(id);
        return ResponseEntity.ok(toResponse(course));
    }

    @PutMapping("/api/courses/{id}")
    public ResponseEntity<CourseResponse> editCourse(@PathVariable int id, @RequestBody CourseRequest req) {
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setCode(req.getCode());
        course.setYearLevel(req.getYearLevel());
        Course updated = courseService.editCourse(course, id);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/courses")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest req) {
        Course course = new Course();
        course.setYearLevel(req.getYearLevel());
        course.setCourseName(req.getCourseName());
        course.setCode(req.getCode());
        Course newCourse = courseService.addCourse(course);
        return ResponseEntity.status(201).body(toResponse(newCourse));
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getCode(), course.getCourseName(), course.getId());
    }
}