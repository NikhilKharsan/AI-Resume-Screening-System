package com.scaler.resumescreener.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalysisResult {
    private Double compatibilityScore;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> missingSkills;
    private String overallFeedback;
}
