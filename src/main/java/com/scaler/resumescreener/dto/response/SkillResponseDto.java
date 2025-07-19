package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResponseDto {
    private Long id;
    private String name;
    private String category;
    private Integer jobsCount;
    private Integer candidatesCount;
}