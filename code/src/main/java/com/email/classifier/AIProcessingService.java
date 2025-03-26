package com.email.classifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIProcessingService {

    private final OpenAIClient openAIClient;

    public String processEmail(String emailBody) {
        log.info("Sending email content to OpenAI for classification...");
        String aiResponse = null;
        try {
            aiResponse = openAIClient.sendRequest(emailBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("AI Classification Response: {}", aiResponse);
        return aiResponse;
    }
}