    package com.mycompany.service;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.mycompany.model.courseModel;
    import java.io.IOException;
    import java.net.URI;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpResponse;
    import java.util.Arrays;
    import java.util.List;

    public class courseService {

        private final HttpClient client = HttpClient.newHttpClient();
        private final ObjectMapper mapper = new ObjectMapper();

        public List<courseModel> fetchCoursesByYear(String year)
                throws IOException, InterruptedException {

            // ✅ Build URL with year filter
            String url = "http://localhost:8080/api/courses?year=" + year;
            System.out.println("Fetching: " + url);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());

            // Parse JSON array into list
            courseModel[] courses = mapper.readValue(
                response.body(), courseModel[].class);

            // ✅ Since backend doesn't return yearLevel in JSON,
            //    set it manually based on which year we fetched
            courseModel.YearLevel level =
                courseModel.YearLevel.valueOf(year); // "FIRST" → YearLevel.FIRST

            for (courseModel course : courses) {
                course.setYearLevel(level);
            }

            return Arrays.asList(courses);
        }
    }