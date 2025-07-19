package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeResponseDto {
    private Long id;
    private String fileName;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int analysisCount;
    private String extractedTextPreview; // First 200 characters
}
