package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.dto.Gemini.GeminiCandidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponse {
    private List<GeminiCandidate> candidates;
}
