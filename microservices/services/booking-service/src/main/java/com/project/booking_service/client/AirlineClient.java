package com.project.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.project.payload.response.AirlineResponse;

@FeignClient(name = "airline-core-service")
public interface AirlineClient {

    @GetMapping("/admin")
    AirlineResponse getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId);

}
