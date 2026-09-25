package com.example.coursevault.service;

import com.example.coursevault.dto.AiSummaryResponse;
import com.example.coursevault.exception.ResourceNotFoundException;
import com.example.coursevault.model.AiContent;
import com.example.coursevault.model.CourseResource;
import com.example.coursevault.repositorie.AiContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class AiSummaryService {

    private final AiContentRepository aiContentRepository;
    private final CourseResourceService courseResourceService;
    private final PdfTextExtractor pdfTextExtractor;
    private final GeminiClient geminiClient;

    public AiSummaryService(AiContentRepository aiContentRepository,
                            CourseResourceService courseResourceService,
                            PdfTextExtractor pdfTextExtractor,
                            GeminiClient geminiClient) {
        this.aiContentRepository = aiContentRepository;
        this.courseResourceService = courseResourceService;
        this.pdfTextExtractor = pdfTextExtractor;
        this.geminiClient = geminiClient;
    }

    public AiSummaryResponse getOrGenerate(int resourceId) throws IOException {
        // 1. Check cache
        Optional<AiContent> cached = aiContentRepository.findById(resourceId);
        if (cached.isPresent()) {
            AiContent content = cached.get();
            return new AiSummaryResponse(
                    content.getResourceId(),
                    content.getSummary(),
                    content.getGeneratedAt(),
                    true  // cached
            );
        }

        // 2. Cache miss — load the resource
        CourseResource resource = courseResourceService.findResource(resourceId);

        // 3. Extract text from the PDF
        String text = pdfTextExtractor.extract(resource.getFilePath());

        if (text == null || text.isBlank()) {
            throw new RuntimeException("PDF contains no extractable text.");
        }

        // 4. Call Gemini
        String summary = geminiClient.generateSummary(text);

        // 5. Save to cache
        AiContent content = new AiContent(resourceId, summary);
        AiContent saved = aiContentRepository.save(content);

        // 6. Return response
        return new AiSummaryResponse(
                saved.getResourceId(),
                saved.getSummary(),
                saved.getGeneratedAt(),
                false  // freshly generated
        );
    }
}