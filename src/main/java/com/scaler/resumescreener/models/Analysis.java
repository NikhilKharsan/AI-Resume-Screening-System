package com.scaler.resumescreener.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analyses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false)
    private Double compatibilityScore;

    @ElementCollection
    @CollectionTable(name = "analysis_strengths", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "strength")
    private List<String> strengths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "analysis_improvements", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "improvement")
    private List<String> improvements = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "analysis_missing_skills", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "missing_skill")
    private List<String> missingSkills = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String overallFeedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalysisStatus status = AnalysisStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}