package com.project.ancillary_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ancillary_service.model.FlightMeal;

public interface FlightMealRepository extends JpaRepository<FlightMeal, Long> {

    List<FlightMeal> findByFlightId(Long id);

    boolean existsByFlightIdAndMealId(Long id, Long mealId);

}
