package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.AnalysisStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalysisSummaryDto {
    private Long id;
    private Double compatibilityScore;
    private AnalysisStatus status;
}