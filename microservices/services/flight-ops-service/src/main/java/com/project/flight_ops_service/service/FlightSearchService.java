package com.project.flight_ops_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.payload.request.FlightSearchRequest;
import com.project.payload.response.FlightInstanceResponse;

public interface FlightSearchService {

    Page<FlightInstanceResponse> searchFlights(FlightSearchRequest request, Pageable pageable);
}
