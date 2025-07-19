package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Analysis;
import com.scaler.resumescreener.models.AnalysisStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    Optional<Analysis> findByIdAndResumeUserEmail(Long id, String userEmail);

    Optional<Analysis> findByResumeIdAndJobId(Long resumeId, Long jobId);

    @Modifying
    @Transactional
    @Query("UPDATE Analysis a SET a.status = :status WHERE a.id = :analysisId")
    void updateStatus(@Param("analysisId") Long analysisId, @Param("status") AnalysisStatus status);

    List<Analysis> findByResumeId(Long resumeId);

    List<Analysis> findByJobId(Long jobId);

    @Query("SELECT a FROM Analysis a WHERE a.job.id = :jobId ORDER BY a.compatibilityScore DESC")
    List<Analysis> findByJobIdOrderByCompatibilityScoreDesc(@Param("jobId") Long jobId);

    @Query("SELECT a FROM Analysis a WHERE a.job.id = :jobId AND a.compatibilityScore >= :minScore ORDER BY a.compatibilityScore DESC")
    List<Analysis> findByJobIdAndMinScoreOrderByScore(@Param("jobId") Long jobId, @Param("minScore") Double minScore);

    @Query("SELECT COUNT(a) FROM Analysis a WHERE a.job.id = :jobId AND a.status = :status")
    Long countByJobIdAndStatus(@Param("jobId") Long jobId, @Param("status") AnalysisStatus status);

    Page<Analysis> findByJobIdOrderByCompatibilityScoreDesc(Long jobId, Pageable pageable);

    List<Analysis> findByStatus(AnalysisStatus status);
}