package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Analysis;
import com.scaler.resumescreener.models.AnalysisStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  AnalysisRepository extends JpaRepository<Analysis, Long> {
    Optional<Analysis> findByIdAndResumeUserEmail(Long id, String userEmail);

    @Modifying
    @Transactional
    @Query("UPDATE Analysis a SET a.status = :status WHERE a.id = :analysisId")
    void updateStatus(Long analysisId, AnalysisStatus status);

    List<Analysis> findByResumeId(Long resumeId);
}

