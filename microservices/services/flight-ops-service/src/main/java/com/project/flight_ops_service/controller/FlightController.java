package com.project.flight_ops_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.enums.FlightStatus;
import com.project.flight_ops_service.service.FlightService;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.FlightResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid @RequestBody FlightRequest flightRequest,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.createFlight(userId, flightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable) {
        return ResponseEntity.ok(flightService.getFlightsByAirline(
                userId,
                departureAirportId,
                arrivalAirportId,
                pageable

        ));

    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody FlightRequest request) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestBody FlightStatus status) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlightResponse> deleteFlight(
            @PathVariable Long id, @RequestHeader("X-User-Id") Long userId) throws Exception {

        flightService.deleteFlight(userId, id);
        return ResponseEntity.noContent().build();
    }

}
