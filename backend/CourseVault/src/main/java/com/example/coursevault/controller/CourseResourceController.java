package com.example.coursevault.controller;

import com.example.coursevault.dto.AiSummaryResponse;
import com.example.coursevault.dto.CourseResourceResponse;
import com.example.coursevault.model.CourseResource;
import com.example.coursevault.model.ResourceType;
import com.example.coursevault.service.AiSummaryService;
import com.example.coursevault.service.CourseResourceService;
import com.example.coursevault.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CourseResourceController {
    CourseResourceService courseResourceService;
    FileStorageService fileStorageService;
    AiSummaryService aiSummaryService;

    @Autowired
    CourseResourceController(CourseResourceService courseResourceService,
                             FileStorageService fileStorageService,
                             AiSummaryService aiSummaryService) {
        this.courseResourceService = courseResourceService;
        this.fileStorageService = fileStorageService;
        this.aiSummaryService = aiSummaryService;
    }

    @PostMapping("/api/course-resources")
    public ResponseEntity<CourseResourceResponse> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("courseId") int courseId,
            @RequestParam("type") ResourceType type) {

        CourseResource saved = courseResourceService.addResource(file, title, courseId, type);
        return ResponseEntity.status(201).body(toResponse(saved));
    }

    @GetMapping("/api/courses/{courseId}/course-resources")
    public ResponseEntity<List<CourseResourceResponse>> listResources(
            @PathVariable int courseId,
            @RequestParam(required = false) ResourceType type) {

        List<CourseResource> resources = (type == null)
                ? courseResourceService.listByCourse(courseId)
                : courseResourceService.listByCourseAndType(courseId, type);

        List<CourseResourceResponse> response = resources.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/course-resources/{id}")
    public ResponseEntity<CourseResourceResponse> getResource(@PathVariable int id) {
        CourseResource resource = courseResourceService.findResource(id);
        return ResponseEntity.ok(toResponse(resource));
    }

    @GetMapping("/api/course-resources/{id}/download")
    public ResponseEntity<Resource> downloadResource(@PathVariable int id) {
        CourseResource resource = courseResourceService.findResource(id);
        Resource file = fileStorageService.loadFileAsResource(resource.getFilePath());

        String extension = switch (resource.getContentType()) {
            case "application/pdf" -> ".pdf";
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> "";
        };
        String filename = resource.getTitle() + extension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @DeleteMapping("/api/course-resources/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable int id) {
        courseResourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    // ═══════════════════════════════════════════════════════════════
    // NEW: AI Summary endpoint
    // ═══════════════════════════════════════════════════════════════
    @PostMapping("/api/course-resources/{id}/ai-summary")
    public ResponseEntity<AiSummaryResponse> getAiSummary(@PathVariable int id) throws IOException {
        AiSummaryResponse response = aiSummaryService.getOrGenerate(id);
        return ResponseEntity.ok(response);
    }

    private CourseResourceResponse toResponse(CourseResource resource) {
        return new CourseResourceResponse(
                resource.getId(),
                resource.getTitle(),
                resource.getCourse().getId(),
                resource.getType(),
                "/api/course-resources/" + resource.getId() + "/download"
        );
    }
}