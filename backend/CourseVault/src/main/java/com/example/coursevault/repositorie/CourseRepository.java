package com.example.coursevault.repositorie;

import com.example.coursevault.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course,Integer> {
}
