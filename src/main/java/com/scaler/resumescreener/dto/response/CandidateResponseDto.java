package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CandidateResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer totalExperience;
    private String currentCompany;
    private String currentDesignation;
    private String location;
    private List<String> skills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer applicationsCount;
    private ResumeResponseDto resume;
}
