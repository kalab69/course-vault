package com.example.coursevault.repositorie;

import com.example.coursevault.model.ExternalLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalLinkRepository extends JpaRepository<ExternalLink,Integer> {
    List<ExternalLink> findByCourseId(int courseId);
}
