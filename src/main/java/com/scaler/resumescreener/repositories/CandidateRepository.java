package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Candidate> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT c FROM Candidate c WHERE c.firstName ILIKE %:keyword% OR c.lastName ILIKE %:keyword% OR c.email ILIKE %:keyword% OR c.currentCompany ILIKE %:keyword%")
    Page<Candidate> findByKeywordSearch(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT c FROM Candidate c JOIN c.skills s WHERE s.name IN :skillNames")
    List<Candidate> findBySkillsNameIn(@Param("skillNames") List<String> skillNames);

    @Query("SELECT c FROM Candidate c WHERE c.totalExperience >= :minExperience AND c.totalExperience <= :maxExperience")
    List<Candidate> findByExperienceRange(@Param("minExperience") Integer minExperience, @Param("maxExperience") Integer maxExperience);

    @Query("SELECT c FROM Candidate c JOIN c.skills s WHERE s.name IN :skillNames AND c.totalExperience >= :minExperience")
    List<Candidate> findBySkillsAndMinExperience(@Param("skillNames") List<String> skillNames, @Param("minExperience") Integer minExperience);

    @Query("SELECT COUNT(c) FROM Candidate c WHERE c.location ILIKE %:location%")
    Long countByLocationContainingIgnoreCase(@Param("location") String location);
}