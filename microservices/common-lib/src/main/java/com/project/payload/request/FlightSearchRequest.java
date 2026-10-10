package com.project.payload.request;

import java.time.LocalDate;
import java.util.List;

import com.project.enums.CabinClassType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure date is required")
    private LocalDate departureDate;

    @Min(value = 1, message = "at least 1 passenger is required")
    private Integer passengers;

    @NotNull(message = "cabin class is required")
    private CabinClassType cabinClass;

    // filter parameters
    private List<Long> airlines;
    private Double minPrice;
    private Double maxPrice;
    private String departureTimeRange;
    private String arrivalTimeRange;
    private Integer maxDuration;
    private String sortBy;
    private String sortOrder;

}
