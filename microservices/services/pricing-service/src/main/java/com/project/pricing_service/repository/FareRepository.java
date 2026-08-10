package com.project.pricing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pricing_service.model.Fare;

public interface FareRepository extends JpaRepository<Fare, Long> {

}
