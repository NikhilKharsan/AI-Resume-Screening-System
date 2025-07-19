package com.scaler.resumescreener.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobSummaryDto {
    private Long id;
    private String title;
    private String company;
    private String location;
}
