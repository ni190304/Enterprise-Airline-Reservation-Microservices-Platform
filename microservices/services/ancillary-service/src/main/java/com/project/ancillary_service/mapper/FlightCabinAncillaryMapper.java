package com.project.ancillary_service.mapper;

import java.util.List;

import com.project.ancillary_service.model.FlightCabinAncillary;
import com.project.ancillary_service.services.FlightCabinAncillaryResponse;
import com.project.payload.response.InsuranceCoverageResponse;

public class FlightCabinAncillaryMapper {

    public static FlightCabinAncillaryResponse toResponse(
            FlightCabinAncillary flightCabinAncillary,
            List<InsuranceCoverageResponse> coverages) {

        if (flightCabinAncillary == null)
            return null;

        return FlightCabinAncillaryResponse.builder()
                .id(flightCabinAncillary.getId())
                .flightId(flightCabinAncillary.getFlightId())
                .cabinClassId(flightCabinAncillary.getCabinClassId())
                .ancillary(AncillaryMapper.toResponse(
                        flightCabinAncillary.getAncillary(), coverages))
                .available(flightCabinAncillary.getAvailable())
                .maxQuantity(flightCabinAncillary.getMaxQuantity())
                .price(flightCabinAncillary.getPrice())
                .includedInFare(flightCabinAncillary.getIncludedInFare())
                .build();

    }

}
