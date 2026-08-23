package com.project.ancillary_service.model;

import com.project.enums.CoverageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ancillary ancillary;

    @Column(nullable = false)
    private CoverageType coverageType;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1008)
    private String description;

    @Column(nullable = false)
    private Double coverageAmount;

    private String emergencyContact;

    private boolean isFlat = true;
    private String claimCondition;
    private Integer displayOrder;
    private boolean active = true;

}
