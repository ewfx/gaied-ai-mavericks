package com.email.classifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAIClient {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Value("${spring.ai.openai.chat.model}")
    private String openAiModel;

    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    public static final Map<String, List<String>> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("Authentication Issues", Arrays.asList("Invalid Credentials", "Two-Factor Authentication (2FA) Issues", "Password Reset", "Account Lockout"));

        CATEGORY_MAP.put("API Access Issues", Arrays.asList("401 Unauthorized", "403 Forbidden", "API Key Not Working", "Rate Limit Exceeded", "Missing Permissions"));

        CATEGORY_MAP.put("Payment & Billing", Arrays.asList("Payment Failure", "Subscription Issues", "Invoice Requests", "Refund Requests"));

        CATEGORY_MAP.put("Technical Issues", Arrays.asList("Server Downtime", "Slow Response Time", "Database Errors", "Integration Issues"));

        CATEGORY_MAP.put("Product Support", Arrays.asList("Feature Requests", "Bug Reports", "Performance Optimization", "Usage Guidelines"));

        CATEGORY_MAP.put("General Inquiries", Arrays.asList("Account Information", "Company Policies", "Partnership Opportunities"));
    }

    private static final String OPENAI_VISION_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Extracts text from an uploaded image using OpenAI Vision API.
     */
    public String sendRequest(String emailContent) throws IOException {
        Map<String, Object> request = Map.of("model", openAiModel,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are an AI assistant that classifies emails into categories and subcategories."),
                        Map.of("role", "user", "content", "Please classify the following email:\n\n" + emailContent),
                        Map.of("role", "user", "content", "Please provide output in json format containing 2 keys: category and subCategory"),
                        Map.of("role", "user", "content", "please provide optput in this format only and nothing more than that: {\\n  \\\"category\\\": \\\"API Access Issues\\\",\\n  \\\"subCategory\\\": \\\"API Key Not Working\\\"\\n}"),
                        Map.of("role", "user", "content", "Please categorize among these mentioned category and subCategory: " + CATEGORY_MAP.toString())),
                "max_tokens", 500);
        // ✅ Convert Map to JSON string using Jackson
        String requestBody = objectMapper.writeValueAsString(request);
        log.info("Sending request to OpenAI: {}", requestBody); // ✅ Log the request JSON for debugging

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send request to OpenAI
        ResponseEntity<String> responseEntity = restTemplate.exchange(OPENAI_VISION_URL, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonNode jsonResponse = objectMapper.readTree(responseEntity.getBody());
            log.info(responseEntity.getBody());
            return jsonResponse.get("choices").get(0).get("message").get("content").asText();
        } else {
            log.error("OpenAI API Error: {}", responseEntity.getBody());
            return "Error extracting text from image.";
        }
    }
}