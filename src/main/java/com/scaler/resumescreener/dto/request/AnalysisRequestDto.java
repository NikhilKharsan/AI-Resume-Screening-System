package com.scaler.resumescreener.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnalysisRequestDto {
    @NotNull(message = "Resume ID is required")
    private Long resumeId;

    @NotNull(message = "Job ID is required")
    private Long jobId;

    // Remove this method as user email should come from authentication
}