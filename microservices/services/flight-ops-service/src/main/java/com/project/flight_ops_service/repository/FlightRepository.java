package com.project.flight_ops_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.flight_ops_service.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
            select f from Flight f
            where f.airlineId = :airlineId
            and (:depId is null or f.departureAirportId = :depId)
            and (:arrId is null or f.arrivalAirportId = :arrId)
            """)
    Page<Flight> findByAirlineId(@Param("airlineId") Long airlineId,
            @Param("depId") Long depId,
            @Param("arrId") Long arrId,
            Pageable pageable);

    boolean existsByFlightNumberAndIdNot(String flightNumber, Long id);
    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);

}
