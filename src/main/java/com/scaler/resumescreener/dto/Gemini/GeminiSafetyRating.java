package com.scaler.resumescreener.dto.Gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiSafetyRating {
    private String category;
    private String probability;
}
