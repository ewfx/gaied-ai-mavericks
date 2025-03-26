package com.email.classifier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class GmailIMAPService {


    @Value("${spring.mail.password}")
    private String gmailAppPassword;

    @Value("${spring.mail.username}")
    private String gmailUserName;

    private static final String HOST = "imap.gmail.com";
    private static final String LABEL_NAME = "email-classification"; // Folder name for the label

    private final AIProcessingService aiProcessingService;
    private final JiraService jiraService;
    private final CommonsEmailService commonsEmailService;

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void fetchUnreadEmails() {
        log.info("Checking for new unread emails in label '{}'", LABEL_NAME);
        Properties properties = new Properties();
        properties.put("mail.imap.host", HOST);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");

        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(HOST, gmailUserName, gmailAppPassword);

            Folder labelFolder = store.getFolder(LABEL_NAME);
            labelFolder.open(Folder.READ_WRITE);

            Message[] messages = labelFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)); // Fetch unread emails
            log.info("Found {} unread emails in '{}'", messages.length, LABEL_NAME);

            for (Message message : messages) {
                processEmail(message);
                message.setFlag(Flags.Flag.SEEN, true); // Mark email as read
            }

            labelFolder.close(false);
            store.close();
        } catch (Exception e) {
            log.error("Error fetching emails from '{}': ", LABEL_NAME, e);
        }
    }

    private void processEmail(Message message) throws Exception {
        log.info("Processing email from: {}", message.getFrom()[0]);
        log.info("Subject: {}", message.getSubject());

        String content = getTextFromMessage(message);
        log.info("Email Body: {}", content);

        // Send email text to OpenAI for classification
        String classification = aiProcessingService.processEmail(content);
        log.info("AI Classification: {}", classification);
        Map<String, String> extractedData = extractCategoryFromAIResponse(classification);
        String category = extractedData.getOrDefault("category", "Unknown");
        String subCategory = extractedData.getOrDefault("subCategory", "Unknown");

        log.info("Extracted Category: {} | Subcategory: {}", category, subCategory);

        String reporterEmail = message.getFrom()[0].toString();
        String ticketUrl = jiraService.createJiraTicket(message.getSubject(), content, category, subCategory, reporterEmail);

        if (ticketUrl != null) {
            commonsEmailService.sendEmail(reporterEmail, message.getSubject(), ticketUrl);
        }

        log.info("Jira ticket created and auto-reply sent for email: {}", message.getSubject());
    }

    private Map<String, String> extractCategoryFromAIResponse(String aiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, String> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, String>>() {});

            String category = responseMap.getOrDefault("category", "Unknown");
            String subCategory = responseMap.getOrDefault("subCategory", "Unknown");

            log.info("Extracted Category: {} | Subcategory: {}", category, subCategory);
            return Map.of("category", category, "subCategory", subCategory);

        } catch (Exception e) {
            log.error("Error extracting category from AI response", e);
            return Map.of("category", "Unknown", "subCategory", "Unknown");
        }
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    public void listFolders() {
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.ssl.enable", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", gmailUserName, gmailAppPassword);

            Folder[] folders = store.getDefaultFolder().list();
            for (Folder folder : folders) {
                System.out.println("Found folder: " + folder.getFullName());
            }

            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            }
        }
        return result.toString();
    }
}