package com.scaler.resumescreener.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String category; // e.g., "Programming", "Framework", "Database"

    @ManyToMany(mappedBy = "requiredSkills")
    @OrderBy("title ASC")
    private List<Job> jobs = new ArrayList<>();

    @ManyToMany(mappedBy = "skills")
    @OrderBy("firstName ASC, lastName ASC")
    private List<Candidate> candidates = new ArrayList<>();
}
