package com.scaler.resumescreener.dto.request;

import lombok.Data;

@Data
public class UploadResumeRequest {
    private String fileName;
    private String jobDescription;
}
