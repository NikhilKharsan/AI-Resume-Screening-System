package com.scaler.resumescreener.dto.response;


import com.scaler.resumescreener.models.Analysis;
import com.scaler.resumescreener.models.AnalysisStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalysisResponseDto {
    private Long analysisId;
    private Long resumeId;
    private String jobDescription;
    private Double compatibilityScore;
    private String strengths;
    private String improvements;
    private String missingSkills;
    private String overallFeedback;
    private AnalysisStatus status;

    private AnalysisResponseDto mapToResponseDto(Analysis analysis) {
        return AnalysisResponseDto.builder()
                .analysisId(analysis.getId())
                .resumeId(analysis.getResume().getId())
                .jobDescription(analysis.getJobDescription())
                .compatibilityScore(Double.valueOf(analysis.getCompatibilityScore()))
                .strengths(analysis.getStrengths())
                .improvements(analysis.getImprovements())
                .missingSkills(analysis.getMissingSkills())
                .overallFeedback(analysis.getOverallFeedback())
                .status(analysis.getStatus())
                .build();
    }
}

