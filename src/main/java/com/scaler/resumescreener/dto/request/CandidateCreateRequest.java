package com.scaler.resumescreener.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
public class CandidateCreateRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @Min(value = 0, message = "Experience cannot be negative")
    private Integer totalExperience;

    private String currentCompany;
    private String currentDesignation;
    private String location;
    private List<String> skills;
}