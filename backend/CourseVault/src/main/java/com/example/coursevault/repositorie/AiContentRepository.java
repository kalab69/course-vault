package com.example.coursevault.repositorie;

import com.example.coursevault.model.AiContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiContentRepository extends JpaRepository<AiContent, Integer> {
    // That's it — JpaRepository gives us everything we need:
    //   - findById(id)
    //   - save(entity)
    //   - deleteById(id)
    //   - existsById(id)
}