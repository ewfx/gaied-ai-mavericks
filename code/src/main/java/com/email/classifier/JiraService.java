package com.email.classifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JiraService {

    @Value("${jira.token}")
    private String jiraToken;

    @Value("${jira.user}")
    private String jiraUser;

    @Value("${jira.host}")
    private String jiraHost;

    private final RestTemplate restTemplate = new RestTemplate();

    public String createJiraTicket(String subject, String emailBody, String category, String subCategory, String reporterEmail) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(jiraUser, jiraToken);

            Map<String, Object> request = Map.of(
                    "fields", Map.of(
                            "project", Map.of("key", "SCRUM"), // Replace with your Jira project key
                            "summary", "[Support Request] " + subject,
                            "description", String.format(
                                    "**Category:** %s\n**Subcategory:** %s\n**Reported By:** %s\n\n**Issue Details:**\n%s",
                                    category, subCategory, reporterEmail, emailBody
                            ),
                            "issuetype", Map.of("name", "Task"), // Change to "Bug" or "Incident" if needed
                            "labels", List.of(category.replace(" ", "_"), subCategory.replace(" ", "_")),
                            "reporter", Map.of("accountId", "712020:2cf94bb8-d7c4-4557-8511-0bae7381f521")
                    )
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(jiraHost, entity, Map.class);
            String ticketId = response.getBody().get("key").toString();
            String ticketUrl = jiraHost.replace("/rest/api/2/issue", "/browse/") + ticketId;

            log.info("Jira ticket created successfully: {}", ticketId);
            return ticketUrl;

        } catch (Exception e) {
            log.error("Error creating Jira ticket", e);
        }
        return "";
    }
}