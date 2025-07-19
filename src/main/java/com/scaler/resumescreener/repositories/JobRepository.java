package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Job;
import com.scaler.resumescreener.models.JobStatus;
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
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByStatusOrderByCreatedAtDesc(JobStatus status, Pageable pageable);

    Page<Job> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Job> findByCreatedByEmailAndStatusOrderByCreatedAtDesc(String email, JobStatus status);

    List<Job> findByCreatedByEmailOrderByCreatedAtDesc(String email);

    Optional<Job> findByIdAndCreatedByEmail(Long id, String email);

    @Query("SELECT j FROM Job j WHERE j.title ILIKE %:keyword% OR j.description ILIKE %:keyword% OR j.company ILIKE %:keyword%")
    Page<Job> findByKeywordSearch(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status = :status AND (j.title ILIKE %:keyword% OR j.description ILIKE %:keyword% OR j.company ILIKE %:keyword%)")
    Page<Job> findByStatusAndKeywordSearch(@Param("status") JobStatus status, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.createdBy.email = :email AND j.status = :status")
    Long countByCreatedByEmailAndStatus(@Param("email") String email, @Param("status") JobStatus status);

    @Query("SELECT j FROM Job j WHERE j.createdAt >= :startDate AND j.createdAt <= :endDate")
    List<Job> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT j FROM Job j JOIN j.requiredSkills s WHERE s.name IN :skillNames")
    List<Job> findByRequiredSkillsNameIn(@Param("skillNames") List<String> skillNames);
}