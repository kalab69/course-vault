package com.example.coursevault.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GroqClient {

    private static final String GROQ_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODEL = "openai/gpt-oss-120b";

    public String generateCourseDescription(String courseName, String courseCode, String yearLevel) {
        String prompt = buildCoursePrompt(courseName, courseCode, yearLevel);
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
                String apiKey = System.getenv("GROQ_API_KEY");
                if (apiKey == null || apiKey.isEmpty()) {
                    throw new RuntimeException("GROQ_API_KEY env variable is not set.");
                }

                String escaped = prompt
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r");

                String requestBody = "{\n"
                        + "  \"model\": \"" + MODEL + "\",\n"
                        + "  \"messages\": [\n"
                        + "    { \"role\": \"user\", \"content\": \"" + escaped + "\" }\n"
                        + "  ]\n"
                        + "}";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(GROQ_URL))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + apiKey)
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

                if ((statusCode == 429 || statusCode >= 500) && attempt < maxAttempts) {
                    long waitMs = (statusCode == 429) ? (attempt * 5000L) : (attempt * 2000L);
                    System.out.println("Groq " + statusCode + " — waiting "
                            + (waitMs / 1000) + "s before retry " + (attempt + 1));
                    Thread.sleep(waitMs);
                    continue;
                }

                System.err.println("Groq HTTP " + statusCode + ": " + body);
                return null;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } catch (Exception e) {
                System.err.println("Groq attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxAttempts) {
                    try { Thread.sleep(attempt * 2000L); } catch (InterruptedException ignored) {}
                }
            }
        }
        return null;
    }

    private String buildCoursePrompt(String courseName, String courseCode, String yearLevel) {
        return "You are describing a university course for a student.\n\n"
                + "Course name: " + courseName + "\n"
                + "Course code: " + courseCode + "\n"
                + "Year level: " + yearLevel + "\n\n"
                + "Write a factual description of this course, in this exact format. "
                + "Do NOT write greetings, introductions, or phrases like "
                + "'Here is a summary' or 'This course provides'. "
                + "Start directly with the first heading.\n\n"
                + "What This Course Covers\n"
                + "(2-3 sentences describing the subject)\n\n"
                + "Main Topics\n"
                + "(bullet list of major topics covered)\n\n"
                + "Who This Is For\n"
                + "(1 sentence on the target student)\n\n"
                + "What You Will Be Able To Do\n"
                + "(3-5 skills or abilities gained)\n\n"
                + "Key Points\n"
                + "(3-5 short important takeaways)";
    }

    private String extractTextFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            // Groq: choices[0].message.content
            JsonNode textNode = root
                    .path("choices").get(0)
                    .path("message")
                    .path("content");

            return textNode.asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}