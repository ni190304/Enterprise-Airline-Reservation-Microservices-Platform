package com.project.flight_ops_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.flight_ops_service.service.FlightScheduleService;
import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.FlightScheduleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-schedules")
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody FlightScheduleRequest flightScheduleRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightScheduleService.createFlightSchedule(
                        userId, flightScheduleRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));

    }

    @GetMapping
    public ResponseEntity<?> getFlightSchedules(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(
                flightScheduleService.getFlightScheduleByAirline(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest request) throws Exception {
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(@PathVariable Long id) throws Exception {
        flightScheduleService.deleteFlightSchedule(id);
        ApiResponse apiResponse = new ApiResponse("schedule removed success");
        return ResponseEntity.ok(apiResponse);
    }

}
