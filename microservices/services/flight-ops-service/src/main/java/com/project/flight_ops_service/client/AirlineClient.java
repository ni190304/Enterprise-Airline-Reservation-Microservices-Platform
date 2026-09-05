package com.project.flight_ops_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;

@FeignClient(name = "airline-core-service")
public interface AirlineClient {

    @GetMapping("/api/airlines/{id}")
    AirlineResponse getAirlineById(
            @PathVariable Long id);

    @GetMapping("/api/aircrafts/{id}")
    AircraftResponse getAircraftById(
            @PathVariable("id") Long id);

}
