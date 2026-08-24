package com.project.ancillary_service.controller;

import java.util.List;

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

import com.project.ancillary_service.services.FlightCabinAncillaryRequest;
import com.project.ancillary_service.services.FlightCabinAncillaryResponse;
import com.project.ancillary_service.services.FlightCabinAncillaryService;
import com.project.enums.AncillaryType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-cabin-ancillaries")
public class FlightCabinAncillaryController {

    private final FlightCabinAncillaryService service;

    @PostMapping
    public ResponseEntity<FlightCabinAncillaryResponse> createFlightCabinAncillary(
            @Valid @RequestBody FlightCabinAncillaryRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> getById(@PathVariable Long id)
            throws Exception {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByFlightAndCabinClass(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId) {
        return ResponseEntity.ok(service.getByFlightAndCabinClass(flightId, cabinClassId));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}")
    public ResponseEntity<FlightCabinAncillaryResponse> getByFlightAndCabinClassAndType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType type) throws Exception {
        return ResponseEntity.ok(
                service.getByFlightIdAndCabinClassIdAndType(flightId, cabinClassId, type));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}/all")
    public ResponseEntity<?> getAllByFlightAndCabinClassAndType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType type) throws Exception {
        return ResponseEntity.ok(
                service.getAllByFlightIdAndCabinClassIdAndType(flightId, cabinClassId, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> update(
            @PathVariable Long id,
            @RequestBody FlightCabinAncillaryRequest request)
            throws Exception {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/price/total")
    public ResponseEntity<?> calculateAncillariesPrice(
            @RequestBody List<Long> flightCabinAncillaryIds) {
        return ResponseEntity.ok(service.calculateAncillaryPrice(flightCabinAncillaryIds));
    }

}
