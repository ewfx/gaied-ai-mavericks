package com.email.classifier.controller;

import com.email.classifier.service.OpenAiVisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final OpenAiVisionService openAiVisionService;

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractTextFromImage(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Processing image: {}", file.getOriginalFilename());
            String extractedText = openAiVisionService.extractTextFromImage(file);
            return ResponseEntity.ok(extractedText);
        } catch (IOException e) {
            log.error("Failed to process image", e);
            return ResponseEntity.status(500).body("Error processing image.");
        }
    }
}
