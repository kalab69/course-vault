package com.example.coursevault.controller;

import com.example.coursevault.dto.ExternalLinkRequest;
import com.example.coursevault.dto.ExternalLinkResponse;
import com.example.coursevault.model.ExternalLink;
import com.example.coursevault.service.ExternalLinkService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExternalLinkController {
    ExternalLinkService externalLinkService;

    @Autowired
    ExternalLinkController(ExternalLinkService externalLinkService) {
        this.externalLinkService = externalLinkService;
    }

    @PostMapping("/api/courses/{courseId}/links")
    public ResponseEntity<ExternalLinkResponse> addExternalLink(
            @PathVariable int courseId,@RequestBody ExternalLinkRequest request) {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setTitle(request.getTitle());
        externalLink.setUrl(request.getUrl());
        externalLink.setTopic(request.getTopic());
        Optional<ExternalLink> createdLink = externalLinkService.addLink(externalLink, courseId);
        if (createdLink.isPresent()) {
            ExternalLinkResponse response = new ExternalLinkResponse(
                    createdLink.get().getId(),
                    createdLink.get().getCourse(),
                    createdLink.get().getTitle()
                    , createdLink.get().getTopic(),
                    createdLink.get().getUrl()
            );
            return ResponseEntity.status(201).body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/courses/{courseId}/links")
    public ResponseEntity<List<ExternalLinkResponse>> getLinks(@PathVariable int courseId) {
        List<ExternalLink> links = externalLinkService.listLinksByCourse(courseId);

        List<ExternalLinkResponse> response = links.stream().map(link ->
                new ExternalLinkResponse(
                        link.getId(),
                        link.getCourse(),
                        link.getTitle()
                        , link.getTopic(),
                        link.getUrl()
                )
        ).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable int id){
        if(externalLinkService.deleteLink(id)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


