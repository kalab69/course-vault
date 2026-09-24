/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.model.courseResourceModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * @author Abreham
 */
public class courseResourceService {
     public List<courseResourceModel>
            fetchCourseResources(int courseId)
            throws Exception {

        HttpClient client
                = HttpClient.newHttpClient();

        HttpRequest request
                = HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "http://localhost:8080/api/courses/"
                                        + courseId
                                        + "/course-resources"
                                )
                        )
                        .GET()
                        .build();

        HttpResponse<String> response
                = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        ObjectMapper mapper
                = new ObjectMapper();

        return mapper.readValue(
                response.body(),
                new TypeReference<List<courseResourceModel>>() {
        }
        );
    }
}
