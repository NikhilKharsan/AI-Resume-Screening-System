package com.scaler.resumescreener.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class JobCreateRequest {
    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Company name is required")
    private String company;

    private String location;

    private Integer minExperience;
    private Integer maxExperience;

    @Positive(message = "Minimum salary must be positive")
    private Double minSalary;

    @Positive(message = "Maximum salary must be positive")
    private Double maxSalary;

    private List<String> requiredSkills;
}
