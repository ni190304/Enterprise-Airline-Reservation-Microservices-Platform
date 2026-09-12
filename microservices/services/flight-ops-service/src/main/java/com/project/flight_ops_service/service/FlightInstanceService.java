package com.project.flight_ops_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.response.FlightInstanceResponse;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request) throws Exception;

    FlightInstanceResponse getFlightInstanceById(Long id) throws Exception; 

    Page<FlightInstanceResponse> getByAirlineId(Long userId,
    Long departureAirportId,
    Long arrivalAirportId,
    Long flightId,
    LocalDate onDate,
    Pageable pageable);

    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception;
    void deleteFlightInstance(Long id) throws Exception;

}
