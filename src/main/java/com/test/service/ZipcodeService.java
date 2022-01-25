package com.test.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;

import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.io.InputStreamReader;

public class ZipcodeService {

    final String key;
    final ObjectMapper objectMapper;
    String GET_STR = "GET";

    public ZipcodeService() {
        this.key = "Du6qoQiY0wRpgJQ6Va997t6jdjSgqS480VyQkWf5WJjIzauEaqrvhtNOQldHEanO";
        objectMapper = new ObjectMapper();
    }

    public String fetchDataFromServer(final String url) throws IOException{
        final URL u = new URL(url);
        final HttpsURLConnection http = (HttpsURLConnection) u.openConnection();
        http.setRequestMethod(GET_STR);
        return fetchContent(http, 0);
    }

    public String fetchContent(final URLConnection conn, final int timeout) throws IOException {
        conn.setAllowUserInteraction(true);
        if(timeout != 0) {
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
        }
        return IOUtils.toString(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

    }

    public String pickFirstZip(String multiDistanceURL, String currentZip, String...targetZip) throws IOException, InterruptedException {
        String target = String.join(",", targetZip);
        String urlLink = String.format(multiDistanceURL,key, currentZip, target);
        String response = fetchDataFromServer(urlLink);System.out.println("multiDistanceURL response = "+response);
        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode distances = jsonNode.get("distances");
        List<String> zipcodes = new ArrayList<>();
        Iterator<String> iterator = distances.fieldNames();
        iterator.forEachRemaining(zipcodes::add);
        return zipcodes.get(0);
    }

    public String retrieveDistance(String singleDistanceURL, String currentZip, String targetZip) throws IOException, InterruptedException {
        String urlLink = String.format(singleDistanceURL,key, currentZip, targetZip);
        String response = fetchDataFromServer(urlLink);System.out.println("singleDistanceURL response = "+response);
        JsonNode jsonNode = objectMapper.readTree(response);
        BigDecimal distances = jsonNode.get("distance").decimalValue();

        return distances.toString();
    }
}
