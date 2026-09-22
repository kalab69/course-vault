package com.example.coursevault.repositorie;

import com.example.coursevault.model.CourseResource;
import com.example.coursevault.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseResourceRepository extends JpaRepository<CourseResource,Integer> {
    List<CourseResource> findByCourseId(int courseId);
    List<CourseResource> findByCourseIdAndType(int courseId, ResourceType type);
}
