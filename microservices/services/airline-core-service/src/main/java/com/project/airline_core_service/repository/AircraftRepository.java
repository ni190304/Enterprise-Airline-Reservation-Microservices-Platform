package com.project.airline_core_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.airline_core_service.model.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft,Long>{

    List<Aircraft> findByAirlineId(Long airlineId);
    boolean existsByCode(String code);
    Aircraft findByIdAndAirlineId(Long id, Long airlineId);
    boolean existsByCodeAndId(String code, Long id);

}
