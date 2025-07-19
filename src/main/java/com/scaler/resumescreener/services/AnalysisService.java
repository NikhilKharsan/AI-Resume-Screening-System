package com.scaler.resumescreener.services;
import com.scaler.resumescreener.exceptions.AnalysisNotFoundException;
import com.scaler.resumescreener.models.*;
import com.scaler.resumescreener.config.RabbitConfig;
import com.scaler.resumescreener.dto.request.AnalysisRequestDto;
import com.scaler.resumescreener.dto.response.AnalysisResponseDto;
import com.scaler.resumescreener.exceptions.ResumeNotFoundException;
import com.scaler.resumescreener.repositories.AnalysisRepository;
import com.scaler.resumescreener.repositories.ResumeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@EnableCaching
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final ResumeRepository resumeRepository;
    private final GeminiAIService aiService;
    private final RabbitTemplate rabbitTemplate;
    private final CacheManager cacheManager;

    public AnalysisResponseDto initiateAnalysis(AnalysisRequestDto request, String userEmail) {
        Resume resume = resumeRepository.findByIdAndUserEmail(request.getResumeId(), userEmail)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found"));

        // Create analysis record
        Analysis analysis = Analysis.builder()
                .resume(resume)
                .jobDescription(request.getJobDescription())
                .status(AnalysisStatus.PENDING)
                .build();

        Analysis savedAnalysis = analysisRepository.save(analysis);

        // Send to queue for async processing
        AnalysisMessage message = AnalysisMessage.builder()
                .analysisId(savedAnalysis.getId())
                .resumeText(resume.getExtractedText())
                .jobDescription(request.getJobDescription())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitConfig.ANALYSIS_EXCHANGE,
                RabbitConfig.ANALYSIS_ROUTING_KEY,
                message
        );

        log.info("Analysis initiated for resume ID: {}", request.getResumeId());

        return mapToResponseDto(savedAnalysis);
    }

    @RabbitListener(queues = RabbitConfig.ANALYSIS_QUEUE)
    public void processAnalysis(AnalysisMessage message) {
        try {
            log.info("Processing analysis for ID: {}", message.getAnalysisId());

            // Update status to processing
            updateAnalysisStatus(message.getAnalysisId(), AnalysisStatus.PENDING );

            // Call AI service
            CompletableFuture<AnalysisResult> futureResult = aiService.analyzeResumeAsync(
                    message.getResumeText(), message.getJobDescription());

            AnalysisResult result = futureResult.get();

            // Update analysis with results
            updateAnalysisWithResults(message.getAnalysisId(), result);

            log.info("Analysis completed for ID: {}", message.getAnalysisId());

        } catch (Exception e) {
            log.error("Failed to process analysis for ID: {}", message.getAnalysisId(), e);
            updateAnalysisStatus(message.getAnalysisId(), AnalysisStatus.FAILED);
        }
    }

    @ Cacheable(value = "analyses", key = "#analysisId")
    public AnalysisResponseDto getAnalysis(Long analysisId, String userEmail) {
        Analysis analysis = analysisRepository.findByIdAndResumeUserEmail(analysisId, userEmail)
                .orElseThrow(() -> new AnalysisNotFoundException("Analysis not found"));

        return mapToResponseDto(analysis);
    }

    private void updateAnalysisStatus(Long analysisId, AnalysisStatus status) {
        analysisRepository.updateStatus(analysisId, status);
    }

    private void updateAnalysisWithResults(Long analysisId, AnalysisResult result) {
        Analysis analysis = analysisRepository.findById(analysisId)
                .orElseThrow(() -> new AnalysisNotFoundException("Analysis not found"));

        analysis.setCompatibilityScore(result.getCompatibilityScore());
        analysis.setStrengths(String.join(", ", result.getStrengths()));
        analysis.setImprovements(String.join(", ", result.getImprovements()));
        analysis.setMissingSkills(String.join(", ", result.getMissingSkills()));
        analysis.setOverallFeedback(result.getOverallFeedback());
        analysis.setStatus(AnalysisStatus.COMPLETED);

        analysisRepository.save(analysis);

        // Clear cache
        if (cacheManager.getCache("analyses") != null) {
            cacheManager.getCache("analyses").evict(analysisId);
        }
    }

    private AnalysisResponseDto mapToResponseDto(Analysis analysis) {
        return AnalysisResponseDto.builder()
                .analysisId(analysis.getId())
                .resumeId(analysis.getResume().getId())
                .jobDescription(analysis.getJobDescription())
                .compatibilityScore(analysis.getCompatibilityScore())
                .strengths(analysis.getStrengths())
                .improvements(analysis.getImprovements())
                .missingSkills(analysis.getMissingSkills())
                .overallFeedback(analysis.getOverallFeedback())
                .status(analysis.getStatus())
                .build();
    }

    public List<AnalysisResponseDto> getAnalysisByResumeId(Long resumeId) {
        List<Analysis> analyses = analysisRepository.findByResumeId(resumeId);

        return analyses.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

}
