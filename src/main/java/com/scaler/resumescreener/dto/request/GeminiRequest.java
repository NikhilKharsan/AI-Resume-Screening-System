package com.scaler.resumescreener.dto.request;

import com.scaler.resumescreener.config.GeminiGenerationConfig;
import com.scaler.resumescreener.dto.Gemini.GeminiContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {
    private List<GeminiContent> contents;
    private GeminiGenerationConfig generationConfig;
}
