package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class JobResponseDto {
    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private Integer minExperience;
    private Integer maxExperience;
    private Double minSalary;
    private Double maxSalary;
    private JobStatus status;
    private List<String> requiredSkills;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer applicationsCount;
}
