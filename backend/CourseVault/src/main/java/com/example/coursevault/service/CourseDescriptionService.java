package com.example.coursevault.service;

import com.example.coursevault.dto.CourseDescriptionResponse;
import com.example.coursevault.exception.ResourceNotFoundException;
import com.example.coursevault.model.Course;
import com.example.coursevault.model.CourseDescription;
import com.example.coursevault.repositorie.CourseDescriptionRepository;
import com.example.coursevault.repositorie.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CourseDescriptionService {

    private final CourseDescriptionRepository courseDescriptionRepository;
    private final CourseRepository courseRepository;
    private final GroqClient groqClient;
    public CourseDescriptionService(CourseDescriptionRepository courseDescriptionRepository,
                                    CourseRepository courseRepository,
                                    GroqClient groqClient) {
        this.courseDescriptionRepository = courseDescriptionRepository;
        this.courseRepository = courseRepository;
        this.groqClient = groqClient;
    }
    public CourseDescriptionResponse getOrGenerate(int courseId) {
        // 1. Check cache
        Optional<CourseDescription> cached = courseDescriptionRepository.findById(courseId);
        if (cached.isPresent()) {
            CourseDescription c = cached.get();
            return new CourseDescriptionResponse(
                    c.getCourseId(),
                    c.getDescription(),
                    c.getGeneratedAt(),
                    true  // cached
            );
        }

        // 2. Load the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        // 3. Ask Gemini to describe the course (no PDF reading)
        String description = groqClient.generateCourseDescription(
                course.getCourseName(),
                course.getCode(),
                course.getYearLevel().name()
        );

        // 4. Save to cache
        CourseDescription saved = courseDescriptionRepository.save(
                new CourseDescription(courseId, description)
        );

        // 5. Return response
        return new CourseDescriptionResponse(
                saved.getCourseId(),
                saved.getDescription(),
                saved.getGeneratedAt(),
                false  // freshly generated
        );
    }
}