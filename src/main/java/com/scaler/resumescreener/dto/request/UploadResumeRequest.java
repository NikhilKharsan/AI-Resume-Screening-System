package com.scaler.resumescreener.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadResumeRequest {
    @NotBlank(message = "File name is required")
    private String fileName;
}
