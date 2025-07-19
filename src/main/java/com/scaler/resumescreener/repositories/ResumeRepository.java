package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Resume;
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
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByIdAndUserEmail(Long id, String userEmail);

    Page<Resume> findByUserEmailOrderByCreatedAtDesc(String userEmail, Pageable pageable);

    List<Resume> findByUserEmail(String userEmail);

    @Query("SELECT r FROM Resume r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    List<Resume> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Resume r WHERE r.user.email = :userEmail")
    Long countByUserEmail(@Param("userEmail") String userEmail);

    @Query("SELECT r FROM Resume r WHERE r.createdAt >= :startDate AND r.createdAt <= :endDate")
    List<Resume> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
