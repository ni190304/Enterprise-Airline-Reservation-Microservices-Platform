package com.project.pricing_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pricing_service.model.BaggagePolicy;

public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy, Long> {

    BaggagePolicy findByFareId(Long fareId);

    List<BaggagePolicy> findByAirlineId(Long airlineId);

    boolean existsByFareId(Long fareId);


}
