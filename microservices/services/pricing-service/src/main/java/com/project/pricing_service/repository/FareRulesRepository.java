package com.project.pricing_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pricing_service.model.FareRules;

public interface FareRulesRepository extends JpaRepository<FareRules, Long> {

    FareRules findByFareId(Long fareId);

    List<FareRules> findByAirlineId(Long airlineId);

    boolean existsByFareId(Long fareId);

}
