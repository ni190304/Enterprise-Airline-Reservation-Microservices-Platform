package com.project.ancillary_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ancillary_service.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByAirlineId(Long airlineId);

    boolean existsByCodeAndAirlineId(String code, Long airlineId);

    boolean existsByAirlineIdAndCodeAndIdNot(Long airlineId, String code, Long id);

}
