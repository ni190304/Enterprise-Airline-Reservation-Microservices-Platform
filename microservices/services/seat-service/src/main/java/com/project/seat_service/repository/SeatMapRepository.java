package com.project.seat_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.seat_service.model.SeatMap;

public interface SeatMapRepository extends JpaRepository<SeatMap, Long> {

    SeatMap findByCabinClassId(Long cabinClassId);

    boolean existsByAirlineIdAndCabinClassIdAndName(Long airlineId, Long cabinClassId, String name);

}
