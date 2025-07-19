package com.scaler.resumescreener.dto.Gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCandidate {
    private GeminiContent content;
    private String finishReason;
    private Integer index;
    private GeminiSafetyRating[] safetyRatings;
}