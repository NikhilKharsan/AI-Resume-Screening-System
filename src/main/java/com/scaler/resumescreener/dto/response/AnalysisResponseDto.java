package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.AnalysisStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AnalysisResponseDto {
    private Long id;
    private Long resumeId;
    private Long jobId;
    private String jobTitle;
    private Double compatibilityScore;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> missingSkills;
    private String overallFeedback;
    private AnalysisStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}