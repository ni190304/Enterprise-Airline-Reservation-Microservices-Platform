package com.project.location_service.service;

import com.project.payload.response.AirportResponse;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request);
}
