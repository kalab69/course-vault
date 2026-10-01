package com.example.coursevault.repositorie;

import com.example.coursevault.model.CourseDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDescriptionRepository extends JpaRepository<CourseDescription, Integer> {
}