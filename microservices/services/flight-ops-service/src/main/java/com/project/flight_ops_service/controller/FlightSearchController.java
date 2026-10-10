package com.project.flight_ops_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.enums.CabinClassType;
import com.project.flight_ops_service.service.FlightSearchService;
import com.project.payload.request.FlightSearchRequest;
import com.project.payload.response.FlightInstanceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @GetMapping("/api/flights/search")
    public ResponseEntity<Page<FlightInstanceResponse>> searchFlights(
            @RequestParam Long departureAirportId,
            @RequestParam Long arrivalAirportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam Integer passengers,
            @RequestParam CabinClassType cabinClass,
            @RequestParam(required = false) List<Long> airlines,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String departureTimeRange,
            @RequestParam(required = false) String arrivalTimeRange,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            Pageable pageable) {
        FlightSearchRequest request = FlightSearchRequest.builder()
                .departureAirportId(departureAirportId)
                .arrivalAirportId(arrivalAirportId)
                .departureDate(departureDate)
                .passengers(passengers)
                .cabinClass(cabinClass)
                .airlines(airlines)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .departureTimeRange(departureTimeRange)
                .arrivalTimeRange(arrivalTimeRange)
                .maxDuration(maxDuration)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        return ResponseEntity.ok(
                flightSearchService.searchFlights(request, pageable));
    }
}
