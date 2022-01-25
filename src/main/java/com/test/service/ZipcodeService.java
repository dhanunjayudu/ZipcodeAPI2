package com.test.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

public class ZipcodeService {

    final String key;
    final ObjectMapper objectMapper;

    public ZipcodeService() {
        this.key = "Du6qoQiY0wRpgJQ6Va997t6jdjSgqS480VyQkWf5WJjIzauEaqrvhtNOQldHEanO";
        objectMapper = new ObjectMapper();
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String pickFirstZip(String multiDistanceURL, String currentZip, String...targetZip) throws IOException, InterruptedException {
        String target = String.join(",", targetZip);

        HttpResponse<String> response = getStringHttpResponse(String.format(multiDistanceURL,key, currentZip, target));
        System.out.println("multiDistanceURL response = "+response);
        System.out.println("multiDistanceURL response body = "+response.body());
        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode distances = jsonNode.get("distances");
        List<String> zipcodes = new ArrayList<>();
        Iterator<String> iterator = distances.fieldNames();
        iterator.forEachRemaining(zipcodes::add);
        return zipcodes.get(0);
    }

    public String retrieveDistance(String singleDistanceURL, String currentZip, String targetZip) throws IOException, InterruptedException {

        HttpResponse<String> response = getStringHttpResponse(String.format(singleDistanceURL,key, currentZip, targetZip));
        System.out.println("singleDistanceURL response = "+response);
        System.out.println("singleDistanceURL response body = "+response.body());
        JsonNode jsonNode = objectMapper.readTree(response.body());
        BigDecimal distances = jsonNode.get("distance").decimalValue();

        return distances.toString();
    }

    private HttpResponse<String> getStringHttpResponse(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
