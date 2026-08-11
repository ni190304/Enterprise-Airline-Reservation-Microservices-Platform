package com.project.pricing_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pricing_service.model.Fare;

public interface FareRepository extends JpaRepository<Fare, Long> {

    boolean existsByFlightIdAndCabinClassIdAndName(
            Long flightId, Long cabinClassId, String name);

    List<Fare> findByFlightIdInAndCabinClassId(List<Long> flightId, Long cabinClassId);

    List<Fare> findByFlightIdAndCabinClassId(Long flightId, Long cabinClassId);

    boolean existsByFlightIdAndCabinClassIdAndNameAndIdNot(Long flightId, Long cabinClassId, String name, Long id);

}
