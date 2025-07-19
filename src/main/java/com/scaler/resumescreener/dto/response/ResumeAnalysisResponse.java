package com.scaler.resumescreener.dto.response;

import lombok.Data;

@Data
public class ResumeAnalysisResponse {
    private int compatibilityScore;
    private String strengths;
    private String improvements;
    private String missingSkills;
    private String overallFeedback;
}
