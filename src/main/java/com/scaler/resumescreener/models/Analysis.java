package com.scaler.resumescreener.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Column(nullable = false)
    private double compatibilityScore;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "analysis_id")
    private List<String> strengths = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "analysis_id")
    private List<String> improvements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "analysis_id")
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
}