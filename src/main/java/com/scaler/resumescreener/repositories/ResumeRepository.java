package com.scaler.resumescreener.repositories;


import com.scaler.resumescreener.dto.response.ResumeResponseDto;
import com.scaler.resumescreener.models.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByIdAndUserEmail(Long id, String userEmail);

    Page<Resume> findByUserEmailOrderByCreatedAtDesc(String userEmail, Pageable pageable);


}