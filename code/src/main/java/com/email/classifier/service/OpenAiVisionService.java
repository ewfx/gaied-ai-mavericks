package com.email.classifier.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiVisionService {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String OPENAI_VISION_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Extracts text from an uploaded image using OpenAI Vision API.
     */
    public String extractTextFromImage(MultipartFile file) throws IOException {
//        byte[] imageBytes = file.getBytes();
//        String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
        String base64Image = "";
        // JSON payload for OpenAI Vision API
        String requestBody = """
                {
                  "model": "gpt-4.5-preview",
                  "messages": [{
                    "role": "user",
                    "content": [
                      { "type": "text", "text": "Extract the text from the image, and output just that" }
                    ]
                  }],
                  "max_tokens": 500
                }""";

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
