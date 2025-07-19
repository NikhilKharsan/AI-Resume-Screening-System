package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateSummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer totalExperience;
    private String currentCompany;
}