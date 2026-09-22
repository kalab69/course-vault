package com.example.coursevault.service;

import com.example.coursevault.model.Course;
import com.example.coursevault.model.CourseResource;
import com.example.coursevault.model.ResourceType;
import com.example.coursevault.repositorie.CourseRepository;
import com.example.coursevault.repositorie.CourseResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

    public Optional<CourseResource> addResource(MultipartFile file, String title, int courseId, ResourceType type) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return Optional.empty(); // will fix when adding custom exceptions
        }

        String filePath = fileStorageService.storeFile(file); // validates type/size internally

        CourseResource resource = new CourseResource();
        resource.setTitle(title);
        resource.setCourse(courseOpt.get());
        resource.setType(type);
        resource.setFilePath(filePath);
        resource.setContentType(file.getContentType());

        CourseResource saved = courseResourceRepository.save(resource);
        return Optional.of(saved);
    }

    public List<CourseResource> listByCourse(int courseId) {
        return courseResourceRepository.findByCourseId(courseId);
    }

    public List<CourseResource> listByCourseAndType(int courseId, ResourceType type) {
        return courseResourceRepository.findByCourseIdAndType(courseId, type);
    }

    public Optional<CourseResource> findResource(int id) {
        return courseResourceRepository.findById(id);
    }

    public boolean deleteResource(int id) {
        Optional<CourseResource> findResource = courseResourceRepository.findById(id);
        if (findResource.isPresent()) {
            fileStorageService.deleteFile(findResource.get().getFilePath());
            courseResourceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}