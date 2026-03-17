package com.examly.springapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    private String positionTitle;
    private String description;
    private String location;
    private String experienceRequired;
    private int openings;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public JobPosition() {}
}
