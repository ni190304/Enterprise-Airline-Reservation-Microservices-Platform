package com.project.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.payload.response.FlightResponse;

@FeignClient ("flight-ops-service")
public interface FlightClient {

    @GetMapping ("/api/flights/{id}")
    FlightResponse getFlightById(@PathVariable Long id);

}
