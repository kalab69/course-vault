package com.example.coursevault.service;

import com.example.coursevault.exception.ResourceNotFoundException;
import com.example.coursevault.model.Course;
import com.example.coursevault.model.CourseResource;
import com.example.coursevault.model.ResourceType;
import com.example.coursevault.repositorie.CourseRepository;
import com.example.coursevault.repositorie.CourseResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseResourceService {
    CourseResourceRepository courseResourceRepository;
    CourseRepository courseRepository;
    FileStorageService fileStorageService;

    @Autowired
    CourseResourceService(CourseResourceRepository courseResourceRepository,
                          CourseRepository courseRepository,
                          FileStorageService fileStorageService) {
        this.courseResourceRepository = courseResourceRepository;
        this.courseRepository = courseRepository;
        this.fileStorageService = fileStorageService;
    }

    public CourseResource addResource(MultipartFile file, String title, int courseId, ResourceType type) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        String filePath = fileStorageService.storeFile(file);

        CourseResource resource = new CourseResource();
        resource.setTitle(title);
        resource.setCourse(course);
        resource.setType(type);
        resource.setFilePath(filePath);
        resource.setContentType(file.getContentType());

        return courseResourceRepository.save(resource);
    }

    public List<CourseResource> listByCourse(int courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found: " + courseId);
        }
        return courseResourceRepository.findByCourseId(courseId);
    }

    public List<CourseResource> listByCourseAndType(int courseId, ResourceType type) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found: " + courseId);
        }
        return courseResourceRepository.findByCourseIdAndType(courseId, type);
    }

    public CourseResource findResource(int id) {
        return courseResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + id));
    }

    public void deleteResource(int id) {
        CourseResource resource = courseResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + id));

        fileStorageService.deleteFile(resource.getFilePath());
        courseResourceRepository.deleteById(id);
    }
}