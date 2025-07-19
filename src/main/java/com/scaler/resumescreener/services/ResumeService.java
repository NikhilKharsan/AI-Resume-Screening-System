package com.scaler.resumescreener.services;

import com.scaler.resumescreener.dto.response.ResumeResponseDto;
import com.scaler.resumescreener.exceptions.*;
import com.scaler.resumescreener.models.Resume;
import com.scaler.resumescreener.models.User;
import com.scaler.resumescreener.repositories.ResumeRepository;
import com.scaler.resumescreener.repositories.UserRepository;
import com.scaler.resumescreener.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@EnableAsync
@EnableRetry
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    @Value("${app.upload.path}")
    private String uploadPath;

    public ResumeResponseDto uploadResume(MultipartFile file, String jobDescription) {
        try {
            // For now, we'll use a default user email or you can get it from SecurityContext
            String userEmail = "default@example.com"; // TODO: Get from authentication context

            // Fetch user
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // Validate uploaded file
            validateFile(file);

            // Save file to disk
            String filePath = fileUtil.saveFile(file, uploadPath);

            // Extract text content from PDF
            String extractedText = fileUtil.extractTextFromPdf(filePath);

            // Build Resume entity
            Resume resume = Resume.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(filePath)
                    .extractedText(extractedText)
                    .user(user)
                    .build();

            Resume savedResume = resumeRepository.save(resume);

            log.info("Resume uploaded successfully for user: {} with job description: {}", userEmail, jobDescription);

            return mapToResponseDto(savedResume);

        } catch (Exception e) {
            log.error("Failed to upload resume with job description: {}", jobDescription, e);
            throw new FileProcessingException("Failed to process resume file");
        }
    }

    @Cacheable(value = "resumes", key = "#resumeId")
    public ResumeResponseDto getResume(Long resumeId, String userEmail) {
        Resume resume = resumeRepository.findByIdAndUserEmail(resumeId, userEmail)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found"));

        return mapToResponseDto(resume);
    }

    public Page<ResumeResponseDto> getUserResumes(String userEmail, Pageable pageable) {
        Page<Resume> resumes = resumeRepository.findByUserEmailOrderByCreatedAtDesc(userEmail, pageable);
        return resumes.map(this::mapToResponseDto);
    }

    public List<ResumeResponseDto> getAllResumes() {
        try {
            // Get all resumes with default pagination (first 100 records)
            Pageable pageable = PageRequest.of(0, 100);
            Page<Resume> resumesPage = resumeRepository.findAll(pageable);

            List<ResumeResponseDto> resumes = resumesPage.getContent()
                    .stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());

            log.info("Retrieved {} resumes", resumes.size());
            return resumes;

        } catch (Exception e) {
            log.error("Failed to retrieve all resumes", e);
            throw new RuntimeException("Failed to retrieve resumes");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("Uploaded file is empty or missing.");
        }

        if (!Objects.requireNonNull(file.getContentType()).equals("application/pdf")) {
            throw new InvalidFileException("Only PDF files are supported.");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new InvalidFileException("File size exceeds 10MB limit.");
        }
    }

    private ResumeResponseDto mapToResponseDto(Resume resume) {
        return ResumeResponseDto.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .createdAt(resume.getCreatedAt())
                .analysisCount(resume.getAnalyses() != null ? resume.getAnalyses().size() : 0)
                .build();
    }
}