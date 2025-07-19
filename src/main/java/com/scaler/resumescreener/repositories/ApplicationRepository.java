package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Application;
import com.scaler.resumescreener.models.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByJobId(Long jobId);

    List<Application> findByCandidateId(Long candidateId);

    Optional<Application> findByJobIdAndCandidateId(Long jobId, Long candidateId);

    Page<Application> findByJobIdOrderByAppliedAtDesc(Long jobId, Pageable pageable);

    Page<Application> findByCandidateIdOrderByAppliedAtDesc(Long candidateId, Pageable pageable);

    List<Application> findByStatus(ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId AND a.status = :status ORDER BY a.appliedAt DESC")
    List<Application> findByJobIdAndStatusOrderByAppliedAtDesc(@Param("jobId") Long jobId, @Param("status") ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId AND a.analysis.compatibilityScore >= :minScore ORDER BY a.analysis.compatibilityScore DESC")
    List<Application> findByJobIdAndMinCompatibilityScore(@Param("jobId") Long jobId, @Param("minScore") Double minScore);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.id = :jobId AND a.status = :status")
    Long countByJobIdAndStatus(@Param("jobId") Long jobId, @Param("status") ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.createdBy.email = :hrEmail")
    Long countApplicationsForHR(@Param("hrEmail") String hrEmail);

    @Query("SELECT a FROM Application a WHERE a.job.createdBy.email = :hrEmail ORDER BY a.appliedAt DESC")
    List<Application> findByHREmailOrderByAppliedAtDesc(@Param("hrEmail") String hrEmail);

    @Query("SELECT a FROM Application a WHERE a.interviewScheduledAt >= :startDate AND a.interviewScheduledAt <= :endDate")
    List<Application> findInterviewsScheduledBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Page<Application> findByJobIdAndStatusOrderByAppliedAtDesc(Long jobId, ApplicationStatus status, Pageable pageable);
}
