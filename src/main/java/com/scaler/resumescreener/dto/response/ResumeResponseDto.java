package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeResponseDto {
    private Long id;
    private String fileName;
    private LocalDateTime createdAt;
    private int analysisCount;
}
