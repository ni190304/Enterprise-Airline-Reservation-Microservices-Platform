package com.project.flight_ops_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.enums.FlightStatus;
import com.project.flight_ops_service.model.Flight;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.FlightResponse;

public interface FlightService {

    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception;

    Page<FlightResponse> getFlightsByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId,
            Pageable pageable);

    FlightResponse getFlightById(Long id);

    FlightResponse updateFlight(Long id, FlightRequest flightRequest);

    FlightResponse changeStatus(Long id, FlightStatus status);

    void deleteFlight(Long id);

    FlightResponse convertToFlightResponse(Flight flight);

}
