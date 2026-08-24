package com.project.ancillary_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ancillary_service.model.FlightCabinAncillary;
import com.project.enums.AncillaryType;

public interface FlightCabinAncillaryRepository extends JpaRepository<FlightCabinAncillary, Long> {

    List<FlightCabinAncillary> findByFlightIdAndCabinClassId(Long flightId,
            Long cabinClassId);

    FlightCabinAncillary findByFlightIdAndCabinClassIdAndAncillary_Type(Long flightId, Long cabinClassId,
            AncillaryType ancillaryType);

    List<FlightCabinAncillary> findAllByFlightIdAndCabinClassIdAndAncillaryType(
            Long flightId, Long cabinClassId, AncillaryType ancillaryType

    );
}
