package com.example.coursevault.service;

import com.example.coursevault.model.Course;
import com.example.coursevault.model.YearLevel;
import com.example.coursevault.repositorie.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    CourseRepository courseRepository;
    @Autowired
    CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }
    public Course addCourse(Course course){
        return courseRepository.save(course);
    }
    public List<Course> listCourses(){
        return courseRepository.findAll();
    }
    public Optional<Course> findCourse(int id){
        return courseRepository.findById(id);
    }
    public Optional<Course> editCourse(Course course,int id){
        Optional<Course> oldCourse = courseRepository.findById(id);
        if(oldCourse.isPresent()){
            Course existing = oldCourse.get();
            existing.setCourseName(course.getCourseName());
            existing.setCode(course.getCode());
            Course saved = courseRepository.save(existing);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }
    public boolean deleteCourse(int id){
        Optional<Course> findCourse = courseRepository.findById(id);
        if(findCourse.isPresent()){
            courseRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
    public List<Course> filterCourseByYear(YearLevel year){
       return courseRepository.findByYearLevel(year);
    }
}
