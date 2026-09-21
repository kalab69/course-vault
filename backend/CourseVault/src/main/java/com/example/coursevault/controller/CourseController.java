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
import java.util.Optional;

@RestController
public class CourseController {
    CourseService courseService;

    @Autowired
    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/api/courses")
    public ResponseEntity<List<CourseResponse>> listCourses(@RequestParam(required = false) YearLevel year) {
        List<Course> courses = (year == null) ? courseService.listCourses() :
                courseService.filterCourseByYear(year);
        List<CourseResponse> courseResponse = courses.stream().map(course -> new CourseResponse(course.getCode()
                , course.getCourseName(),
                course.getId())
        ).toList();
        return ResponseEntity.ok().body(courseResponse);
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable int id) {
        Optional<Course> course = courseService.findCourse(id);
        if (course.isPresent()) {
            CourseResponse courseResponse = new CourseResponse(course.get().getCode(),
                    course.get().getCourseName(),
                    course.get().getId());
            return ResponseEntity.ok(courseResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/courses/{id}")
    public ResponseEntity<CourseResponse> editCourse(@PathVariable int id, @RequestBody CourseRequest courseRequest) {
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setCode(courseRequest.getCode());
        course.setYearLevel(courseRequest.getYearLevel());
        Optional<Course> updatedCourse = courseService.editCourse(course, id);
        if (updatedCourse.isPresent()) {
            CourseResponse response = new CourseResponse(updatedCourse.get().getCode(),
                    updatedCourse.get().getCourseName(),
                    updatedCourse.get().getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id){
        if(courseService.deleteCourse(id)){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/api/courses")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest){
        Course course = new Course();
        course.setYearLevel(courseRequest.getYearLevel());
        course.setCourseName(courseRequest.getCourseName());
        course.setCode(courseRequest.getCode());
        Course newCourse = courseService.addCourse(course);
        CourseResponse response = new CourseResponse(newCourse.getCode() , newCourse.getCourseName(),newCourse.getId());
        return ResponseEntity.status(201).body(response);
    }



}
