package com.example.coursevault.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiClient {

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent";

    private static final int MAX_CHARS = 20000;

    public String generateSummary(String content) {
        // Truncate BEFORE sending to reduce token usage
        if (content != null && content.length() > MAX_CHARS) {
            content = content.substring(0, MAX_CHARS) + "\n\n[...truncated...]";
        }

        String prompt = buildPrompt(content);
        String rawResponse = sendWithRetry(prompt, 3);

        if (rawResponse == null) {
            throw new RuntimeException(
                    "AI service is currently unavailable. Please try again in a moment.");
        }

        return extractTextFromJson(rawResponse);
    }

    private String sendWithRetry(String prompt, int maxAttempts) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                String apiKey = System.getenv("GEMINI_API_KEY");
                if (apiKey == null || apiKey.isEmpty()) {
                    throw new RuntimeException("GEMINI_API_KEY env variable is not set.");
                }

                String escaped = prompt
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r");

                String requestBody = "{\n"
                        + "  \"contents\": [\n"
                        + "    {\n"
                        + "      \"parts\": [\n"
                        + "        { \"text\": \"" + escaped + "\" }\n"
                        + "      ]\n"
                        + "    }\n"
                        + "  ]\n"
                        + "}";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(GEMINI_URL + "?key=" + apiKey))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                int statusCode = response.statusCode();
                String body = response.body();

                if (statusCode == 200) {
                    return body;
                }

                // Handle quota/retry errors with longer waits
                if ((statusCode == 503 || statusCode == 429) && attempt < maxAttempts) {
                    long waitMs = (statusCode == 429) ? (attempt * 20000L) : (attempt * 3000L);
                    System.out.println("Gemini " + statusCode + " — waiting "
                            + (waitMs / 1000) + "s before retry " + (attempt + 1));
                    Thread.sleep(waitMs);
                    continue;
                }

                System.err.println("Gemini HTTP " + statusCode + ": " + body);
                return null;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } catch (Exception e) {
                System.err.println("Gemini attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxAttempts) {
                    try { Thread.sleep(attempt * 3000L); } catch (InterruptedException ignored) {}
                }
            }
        }
        return null;
    }

    private String buildPrompt(String content) {
        return "You are analyzing a document for a university course library.\n\n"
                + "Write a factual description of this document, in this exact format. "
                + "Do NOT write greetings, introductions, or phrases like "
                + "'Here is a summary' or 'This document provides'. "
                + "Start directly with the first heading.\n\n"
                + "What This Document Covers\n"
                + "(2-3 sentences describing the type and subject of the document)\n\n"
                + "Main Topics\n"
                + "(bullet list of the major topics covered)\n\n"
                + "Who This Is For\n"
                + "(1 sentence on the target audience)\n\n"
                + "What You Will Be Able To Do\n"
                + "(3-5 skills or abilities the reader will gain)\n\n"
                + "Key Points\n"
                + "(3-5 short bullets of the most important takeaways)\n\n"
                + "Document content:\n\n"
                + content;
    }

    private String extractTextFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode textNode = root
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text");
            return textNode.asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}