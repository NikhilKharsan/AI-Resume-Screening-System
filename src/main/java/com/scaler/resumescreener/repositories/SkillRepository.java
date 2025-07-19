package com.scaler.resumescreener.repositories;

import com.scaler.resumescreener.models.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByNameIgnoreCase(String name);

    List<Skill> findByNameInIgnoreCase(List<String> names);

    boolean existsByNameIgnoreCase(String name);

    List<Skill> findByCategory(String category);

    @Query("SELECT DISTINCT s.category FROM Skill s WHERE s.category IS NOT NULL ORDER BY s.category")
    List<String> findDistinctCategories();

    @Query("SELECT s FROM Skill s WHERE s.name ILIKE %:keyword% OR s.category ILIKE %:keyword%")
    Page<Skill> findByKeywordSearch(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Skill s ORDER BY s.name ASC")
    List<Skill> findAllOrderByName();

    @Query("SELECT s FROM Skill s WHERE SIZE(s.jobs) > 0 ORDER BY SIZE(s.jobs) DESC")
    List<Skill> findSkillsWithJobsOrderByPopularity();

    @Query("SELECT s FROM Skill s WHERE SIZE(s.candidates) > 0 ORDER BY SIZE(s.candidates) DESC")
    List<Skill> findSkillsWithCandidatesOrderByPopularity();

    // For bulk operations when creating skills from text
    @Query("SELECT s FROM Skill s WHERE s.name IN :skillNames")
    List<Skill> findByNameIn(@Param("skillNames") Set<String> skillNames);
}