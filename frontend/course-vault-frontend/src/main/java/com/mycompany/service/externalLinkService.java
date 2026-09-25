/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.model.externalLinkModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abreham
 */
public class externalLinkService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<externalLinkModel> fetchCourseLinks(int courseId) {
        try {
            // በቤክኤንድ ከተቀረጸው የኤፒአይ አድራሻ ጋር ማዛመድ
            String targetUrl = "http://localhost:8080/api/courses/" + courseId + "/links";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<externalLinkModel>>() {
                });
            }
        } catch (Exception e) {
            System.err.println("Error fetching external links for course ID " + courseId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
