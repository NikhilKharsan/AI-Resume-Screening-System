package com.scaler.resumescreener.dto.Gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContent {
    private List<GeminiPart> parts;
    private String role; // "user" or "model"
}