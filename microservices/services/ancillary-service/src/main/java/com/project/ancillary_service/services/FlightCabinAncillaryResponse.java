package com.project.ancillary_service.services;

import com.project.payload.response.AncillaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightCabinAncillaryResponse {

    private Long id;
    private Long flightId;
    private Long cabinClassId;
    private AncillaryResponse ancillary;
    private Boolean available;
    private Integer maxQuantity;
    private Double price;
    private Boolean includedInFare;

}
