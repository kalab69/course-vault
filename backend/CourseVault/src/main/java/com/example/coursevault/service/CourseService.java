
package com.example.coursevault.service;



import com.example.coursevault.exception.DuplicateResourceException;
import com.example.coursevault.exception.ResourceNotFoundException;
import com.example.coursevault.model.Course;
import com.example.coursevault.model.YearLevel;
import com.example.coursevault.repositorie.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    CourseRepository courseRepository;

    @Autowired
    CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(Course course) {
        courseRepository.findByCode(course.getCode()).ifPresent(existing -> {
            throw new DuplicateResourceException("Course code already exists: " + course.getCode());
        });
        return courseRepository.save(course);
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course findCourse(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
    }

    public Course editCourse(Course course, int id) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));

        if (!existing.getCode().equals(course.getCode())) {
            courseRepository.findByCode(course.getCode()).ifPresent(other -> {
                throw new DuplicateResourceException("Course code already exists: " + course.getCode());
            });
        }

        existing.setCourseName(course.getCourseName());
        existing.setCode(course.getCode());
        existing.setYearLevel(course.getYearLevel());
        return courseRepository.save(existing);
    }

    public void deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
    }

    public List<Course> filterCourseByYear(YearLevel year) {
        return courseRepository.findByYearLevel(year);
    }
}