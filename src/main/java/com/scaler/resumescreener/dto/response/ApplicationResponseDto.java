package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponseDto {
    private Long id;
    private JobSummaryDto job;
    private CandidateSummaryDto candidate;
    private AnalysisSummaryDto analysis;
    private ApplicationStatus status;
    private String hrNotes;
    private LocalDateTime interviewScheduledAt;
    private LocalDateTime interviewCompletedAt;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}
