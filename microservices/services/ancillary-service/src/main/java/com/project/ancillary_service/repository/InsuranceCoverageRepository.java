package com.project.ancillary_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ancillary_service.model.InsuranceCoverage;

public interface InsuranceCoverageRepository extends JpaRepository<InsuranceCoverage,Long> {

    List<InsuranceCoverage> findByAncillaryId(Long ancillaryId);
    List<InsuranceCoverage> findByAncillaryIdAndActiveTrue(Long ancillaryId);
}
