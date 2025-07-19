package com.scaler.resumescreener.dto.request;

import lombok.Data;

@Data
public class AnalysisRequestDto {
    private Long resumeId;
    private String jobDescription;

    public String getUserEmail() {
        return null;
    }
}
