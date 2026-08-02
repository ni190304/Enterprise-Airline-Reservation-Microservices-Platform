package com.project.airline_core_service.controller;

import java.util.List;

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

import com.project.airline_core_service.service.AircraftService;
import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        AircraftResponse aircraft = aircraftService.createAircraft(aircraftRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> listAllAircrafts(
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        return ResponseEntity.ok(
                aircraftService.listAllAircraftByOwner(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        return ResponseEntity.ok(
                aircraftService.updateAircraft(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        aircraftService.deleteAircraft(id, userId);

        return ResponseEntity.noContent().build();
    }

}
