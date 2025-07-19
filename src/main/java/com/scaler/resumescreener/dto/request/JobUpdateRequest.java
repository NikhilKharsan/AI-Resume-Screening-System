package com.scaler.resumescreener.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class JobUpdateRequest {
    private String title;
    private String description;
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
