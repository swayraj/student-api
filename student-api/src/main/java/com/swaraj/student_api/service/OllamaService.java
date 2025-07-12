package com.swaraj.student_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String generateSummary(String studentDetails) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("model", "llama3.2");
            request.put("prompt", String.format(
                    "Generate a concise 1-sentence summary about student %s. " +
                            "Focus on academic strengths. Format: 'SUMMARY: [content]'",
                    studentDetails));
            request.put("stream", false);

            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            request.put("options", options);

            String response = restTemplate.postForObject(OLLAMA_URL, request, String.class);
            JsonNode responseJson = objectMapper.readTree(response);
            return responseJson.get("response").asText();

        } catch (Exception e) {
            throw new RuntimeException("AI service error: " + e.getMessage());
        }
    }
}