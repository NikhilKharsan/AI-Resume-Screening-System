package com.scaler.resumescreener.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiGenerationConfig {
    private Double temperature;
    private Integer topK;
    private Double topP;
    private Integer maxOutputTokens;
}
