package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeAnalysisResponse {
    private Double compatibilityScore;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> missingSkills;
    private String overallFeedback;
}
