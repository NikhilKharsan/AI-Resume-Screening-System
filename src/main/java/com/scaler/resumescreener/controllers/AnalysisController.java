package com.scaler.resumescreener.controllers;


import com.scaler.resumescreener.dto.request.AnalysisRequestDto;
import com.scaler.resumescreener.services.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping("/{resumeId}/run")
    public ResponseEntity<?> runAnalysis(@PathVariable Long resumeId, @RequestBody AnalysisRequestDto request) {
        return ResponseEntity.ok(analysisService.initiateAnalysis(request, request.getUserEmail()));
    }


    @GetMapping("/{resumeId}")
    public ResponseEntity<?> getAnalysis(@PathVariable Long resumeId) {
        return ResponseEntity.ok(analysisService.getAnalysisByResumeId(resumeId));
    }
}