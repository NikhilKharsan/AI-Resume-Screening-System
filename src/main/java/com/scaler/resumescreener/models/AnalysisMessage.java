package com.scaler.resumescreener.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalysisMessage {
    private Long analysisId;
    private String resumeText;
    private String jobDescription;
}
