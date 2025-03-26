package com.email.classifier;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonsEmailService {

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.password}")
    private String smtpPassword;

    @Value("${spring.mail.port}")
    private int smtpPort;

    public void sendEmail(String toEmail, String subject, String ticketUrl) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setAuthentication(senderEmail, smtpPassword);
            email.setSSLOnConnect(true);
            email.setFrom(senderEmail);
            email.setSubject("Support Ticket Created for " + subject);
            email.setMsg(getBody(subject, ticketUrl));
            email.addTo(toEmail);
            email.send();

            log.info("Email sent successfully to {}", toEmail);
        } catch (EmailException e) {
            log.error("Error sending email via Apache Commons Email", e);
        }
    }

    private String getBody(String subject, String ticketUrl) {
        return "Dear User,\n\nYour support request has been logged in Jira.\n" +
        "\nThis is in regard with your email with subject: " + subject +
        "\n\nYou can track the issue at: " + ticketUrl + "\n\nBest regards,\nSupport Team";
    }
}