package com.scaler.resumescreener.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiUsageMetadata {
    private Integer promptTokenCount;
    private Integer candidatesTokenCount;
    private Integer totalTokenCount;
}
