package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ctdt_thongtinchung")
public class CtdtThongTinChung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String level;

    @Column(name = "degree_type")
    private String degreeType;

    @Column(name = "training_type")
    private String trainingType;

    private Float duration;

    private String faculty;

    private String language;

    private String website;

    private String decision;

    private String version;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CtdtThongTinChung() {}

    public CtdtThongTinChung(String name, String level, String degreeType, String trainingType, Float duration, String faculty, String language, String website, String decision, String version, String status, String description) {
        this.name = name;
        this.level = level;
        this.degreeType = degreeType;
        this.trainingType = trainingType;
        this.duration = duration;
        this.faculty = faculty;
        this.language = language;
        this.website = website;
        this.decision = decision;
        this.version = version;
        this.status = status;
        this.description = description;
    }
}
