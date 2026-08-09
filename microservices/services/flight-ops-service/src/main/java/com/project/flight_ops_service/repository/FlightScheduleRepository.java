package com.project.flight_ops_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight_ops_service.model.FlightSchedule;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

    List<FlightSchedule> findByFlightAirlineId(Long airlineId);

    

}
