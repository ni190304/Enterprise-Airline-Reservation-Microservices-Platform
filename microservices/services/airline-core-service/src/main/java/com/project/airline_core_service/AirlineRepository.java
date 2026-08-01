package com.project.airline_core_service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.airline_core_service.model.Airline;
import java.util.List;
import com.project.enums.AirlineStatus;


public interface AirlineRepository extends JpaRepository<Airline,Long> {

    Optional<Airline> findByOwnerId(Long ownerId);

    List<Airline> findByStatus(AirlineStatus status);

}
