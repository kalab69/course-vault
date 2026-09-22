package com.example.coursevault.service;

import com.example.coursevault.exception.ResourceNotFoundException;
import com.example.coursevault.model.Course;
import com.example.coursevault.model.ExternalLink;
import com.example.coursevault.repositorie.CourseRepository;
import com.example.coursevault.repositorie.ExternalLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalLinkService {
    ExternalLinkRepository externalLinkRepository;
    CourseRepository courseRepository;

    @Autowired
    ExternalLinkService(ExternalLinkRepository externalLinkRepository, CourseRepository courseRepository) {
        this.externalLinkRepository = externalLinkRepository;
        this.courseRepository = courseRepository;
    }

    public ExternalLink addLink(ExternalLink link, int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        link.setCourse(course);
        return externalLinkRepository.save(link);
    }

    public List<ExternalLink> listLinksByCourse(int courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found: " + courseId);
        }
        return externalLinkRepository.findByCourseId(courseId);
    }

    public ExternalLink findLink(int id) {
        return externalLinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found: " + id));
    }

    public ExternalLink editLink(ExternalLink link, int id) {
        ExternalLink existing = externalLinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found: " + id));

        existing.setTopic(link.getTopic());
        existing.setTitle(link.getTitle());
        existing.setUrl(link.getUrl());
        return externalLinkRepository.save(existing);
    }

    public void deleteLink(int id) {
        if (!externalLinkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Link not found: " + id);
        }
        externalLinkRepository.deleteById(id);
    }
}