package com.example.coursevault.service;

import com.example.coursevault.model.ExternalLink;
import com.example.coursevault.repositorie.ExternalLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalLinkService {
    ExternalLinkRepository externalLinkRepository;

    @Autowired
    ExternalLinkService(ExternalLinkRepository externalLinkRepository){
        this.externalLinkRepository = externalLinkRepository;
    }

    public ExternalLink addLink(ExternalLink link){
        return externalLinkRepository.save(link);
    }

    public List<ExternalLink> listLinksByCourse(int courseId){
        return externalLinkRepository.findByCourseId(courseId);
    }

    public Optional<ExternalLink> findLink(int id){
        return externalLinkRepository.findById(id);
    }

    public Optional<ExternalLink> editLink(ExternalLink link, int id){
        Optional<ExternalLink> oldLink = externalLinkRepository.findById(id);
        if(oldLink.isPresent()){
            ExternalLink existing = oldLink.get();
            existing.setTopic(link.getTopic());
            existing.setTitle(link.getTitle());
            existing.setUrl(link.getUrl());
            ExternalLink saved = externalLinkRepository.save(existing);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteLink(int id){
        Optional<ExternalLink> findLink = externalLinkRepository.findById(id);
        if(findLink.isPresent()){
            externalLinkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}