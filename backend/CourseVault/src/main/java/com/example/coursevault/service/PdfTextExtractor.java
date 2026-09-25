package com.example.coursevault.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfTextExtractor {

    private final FileStorageService fileStorageService;

    public PdfTextExtractor(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public String extract(String storedFilename) throws IOException {
        // Ask FileStorageService to resolve the filename to a real file
        Resource resource = fileStorageService.loadFileAsResource(storedFilename);

        try (InputStream inputStream = resource.getInputStream();
             PDDocument doc = Loader.loadPDF(inputStream.readAllBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(doc);
        }
    }
}