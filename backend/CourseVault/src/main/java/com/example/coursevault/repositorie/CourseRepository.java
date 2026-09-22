package com.example.coursevault.repositorie;

import com.example.coursevault.model.Course;
import com.example.coursevault.model.YearLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByYearLevel(YearLevel yearLevel);

    Optional<Course> findByCode(String code);
}