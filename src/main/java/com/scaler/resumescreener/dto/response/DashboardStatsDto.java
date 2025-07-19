package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsDto {
    private long totalJobs;
    private long activeJobs;
    private long totalCandidates;
    private long totalApplications;
    private long pendingAnalyses;
    private long interviewsScheduled;
    private double averageCompatibilityScore;
}