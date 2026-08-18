package com.project.seat_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.request.FlightInstanceCabinRequest;
import com.project.payload.response.FlightInstanceCabinResponse;
import com.project.seat_service.services.FlightInstanceCabinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instance-cabins")
public class FlightInstanceCabinController {

    private final FlightInstanceCabinService flightInstanceCabinService;

    @PostMapping
    public ResponseEntity<FlightInstanceCabinResponse> createFlightInstanceCabin(
            @Valid @RequestBody FlightInstanceCabinRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.createFlightInstanceCabin(request));

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> getFlightInstanceCabinById(
            @PathVariable Long id) {

        return ResponseEntity.ok(flightInstanceCabinService.getFlightInstanceCabinById(id));

    }

    @GetMapping("/flight-instance/{flightInstanceId}/cabin-class/{cabinClassId}")
    public ResponseEntity<?> getByFlightInstanceIdAndCabinClassId(
            @PathVariable Long cabinClassId,
            @PathVariable Long flightInstanceId) {
        return ResponseEntity.ok(
                flightInstanceCabinService.getByFlightInstanceIdAndCabinClassId(
                        flightInstanceId, cabinClassId

                ));

    }

    @GetMapping("/flight-instance/{flightInstanceId}")
    public ResponseEntity<Page<FlightInstanceCabinResponse>> getByFlightInstanceId(
            @PathVariable Long flightInstanceId, Pageable pageable) {
        return ResponseEntity.ok(
                flightInstanceCabinService.getByFlightInstanceId(flightInstanceId, pageable));

    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> updateFlightInstanceCabin(
            @PathVariable Long id,
            @RequestBody FlightInstanceCabinRequest request) {
        return ResponseEntity.ok(
                flightInstanceCabinService.updateFlightInstanceCabin(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightInstanceCabin(@PathVariable Long id) {
        flightInstanceCabinService.deleteFlightInstanceCabin(id);
        return ResponseEntity.noContent().build();
    }
}
