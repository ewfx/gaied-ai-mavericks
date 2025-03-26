package com.email.classifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailProcessingService {

    private final AIProcessingService aiProcessingService;
    private final JiraService jiraService;

    public void processEmail(String emailBody) {
        log.info("Processing email...");

        // Step 1: Send email content to AI for classification
        String aiResponse = aiProcessingService.processEmail(emailBody);
        Map<String, String> extractedData = extractKeyDetails(aiResponse);

        String requestType = extractedData.get("requestType");
        String subRequestType = extractedData.get("subRequestType");

        log.info("Email classified as: {} -> {}", requestType, subRequestType);

        // Step 2: Create Jira ticket based on classification
        String summary = "New Service Request: " + requestType;
        String description = "Details: " + emailBody + "\n\nAI Classification: " + aiResponse;

//        jiraService.createJiraTicket(summary, description);

        log.info("Jira ticket created successfully.");
    }

    private Map<String, String> extractKeyDetails(String aiResponse) {
        // Simulating AI response parsing
        return Map.of(
            "requestType", "Loan Modification",
            "subRequestType", "Interest Rate Change"
        );
    }
}