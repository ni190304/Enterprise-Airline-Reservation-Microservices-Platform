package com.project.location_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.location_service.model.Airport;

public interface AirportRepository extends JpaRepository<Airport,Long>{

    Optional<Airport> findByIataCode(String iatacode);

    List<Airport> findByCityId(Long cityId);

}
