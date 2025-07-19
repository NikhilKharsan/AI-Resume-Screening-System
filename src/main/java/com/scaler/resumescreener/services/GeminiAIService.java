package com.scaler.resumescreener.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.resumescreener.config.GeminiGenerationConfig;
import com.scaler.resumescreener.dto.Gemini.GeminiContent;
import com.scaler.resumescreener.dto.Gemini.GeminiPart;
import com.scaler.resumescreener.dto.request.GeminiRequest;
import com.scaler.resumescreener.dto.response.GeminiResponse;
import com.scaler.resumescreener.exceptions.GeminiAiProcessingException;
import com.scaler.resumescreener.models.AnalysisResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiAIService {

    private final RestTemplate restTemplate;

    @Value("${app.gemini.api-key}")
    private String apiKey;

    @Value("${app.gemini.api-url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent}")
    private String apiUrl;

    @Async
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public CompletableFuture<AnalysisResult> analyzeResumeAsync(
            String resumeText, String jobDescription) {

        try {
            String prompt = buildAnalysisPrompt(resumeText, jobDescription);

            GeminiRequest request = GeminiRequest.builder()
                    .contents(List.of(
                            GeminiContent.builder()
                                    .parts(List.of(
                                            GeminiPart.builder()
                                                    .text(prompt)
                                                    .build()
                                    ))
                                    .build()
                    ))
                    .generationConfig(GeminiGenerationConfig.builder()
                            .temperature(0.3)
                            .topK(40)
                            .topP(0.95)
                            .maxOutputTokens(1024)
                            .build())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);

            HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, entity, GeminiResponse.class);

            String aiResponse = response.getBody().getCandidates().get(0)
                    .getContent().getParts().get(0).getText();

            AnalysisResult result = parseAIResponse(aiResponse);

            log.info("Gemini AI analysis completed successfully");

            return CompletableFuture.completedFuture(result);

        } catch (Exception e) {
            log.error("Failed to analyze resume with Gemini AI", e);
            throw new GeminiAiProcessingException("Failed to process AI analysis: " + e.getMessage());
        }
    }

    private String buildAnalysisPrompt(String resumeText, String jobDescription) {
        return String.format("""
            You are an expert HR assistant. Analyze the following resume against the job description and provide a detailed assessment.
            
            Please analyze and return the response ONLY in the following JSON format (no additional text):
            {
                "compatibilityScore": number between 0-100,
                "strengths": ["strength1", "strength2", "strength3"],
                "improvements": ["improvement1", "improvement2", "improvement3"],
                "missingSkills": ["skill1", "skill2", "skill3"],
                "overallFeedback": "detailed professional feedback about the candidate's fit for this role"
            }
            
            Consider these factors:
            1. Technical skills match
            2. Experience level alignment
            3. Education relevance
            4. Soft skills indicators
            5. Career progression
            6. Industry experience
            
            Resume Content:
            %s
            
            Job Description:
            %s
            """, resumeText, jobDescription);
    }

    private AnalysisResult parseAIResponse(String aiResponse) {
        try {
            // Clean the response to extract JSON
            String jsonResponse = extractJsonFromResponse(aiResponse);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, AnalysisResult.class);
        } catch (Exception e) {
            log.error("Failed to parse Gemini AI response: {}", aiResponse, e);
            // Return default analysis if parsing fails
            return createDefaultAnalysis();
        }
    }

    private String extractJsonFromResponse(String response) {
        // Remove any Markdown formatting or extra text
        String cleaned = response.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        // Find JSON object boundaries
        int start = cleaned.indexOf('{');
        int end = cleaned.lastIndexOf('}');

        if (start != -1 && end != -1 && end > start) {
            return cleaned.substring(start, end + 1);
        }

        return cleaned;
    }

    private AnalysisResult createDefaultAnalysis() {
        return AnalysisResult.builder()
                .compatibilityScore(50.0)
                .strengths(List.of("Resume submitted", "Basic qualifications present", "Professional format"))
                .improvements(List.of("Need more specific details", "Could highlight achievements better", "Consider adding relevant keywords"))
                .missingSkills(List.of("Unable to determine specific missing skills"))
                .overallFeedback("Analysis could not be completed automatically. Manual review recommended.")
                .build();
    }
}