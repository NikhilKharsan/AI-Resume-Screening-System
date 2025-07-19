package com.scaler.resumescreener.controllers;

import com.scaler.resumescreener.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription) {
        return ResponseEntity.ok(resumeService.uploadResume(file, jobDescription));
    }

    @GetMapping
    public ResponseEntity<?> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }
}