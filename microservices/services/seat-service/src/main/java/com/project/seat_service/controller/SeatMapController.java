package com.project.seat_service.controller;

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

import com.project.payload.request.SeatMapRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.SeatMapResponse;
import com.project.seat_service.services.SeatMapService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat-maps")
public class SeatMapController {

    private final SeatMapService seatMapService;

    @PostMapping
    public ResponseEntity<SeatMapResponse> createSeatMap(
            @Valid @RequestBody SeatMapRequest seatMapRequest,
            @RequestHeader("X-Airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        seatMapService.createSeatMap(airlineId, seatMapRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatMapResponse> getSeatMapById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(seatMapService.getSeatMapById(id));
    }

    @GetMapping("/cabin-class/{cabinClassId}")
    public ResponseEntity<SeatMapResponse> getSeatMapsByCabinClass(
            @PathVariable Long cabinClassId) {
        SeatMapResponse responses = seatMapService.getSeatMapByCabinClass(cabinClassId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatMapResponse> updateSeatMap(
            @PathVariable Long id,
            @RequestBody SeatMapRequest request) throws Exception {
        return ResponseEntity.ok(seatMapService.updateSeatMap(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSeatMap(@PathVariable Long id) throws Exception {
        seatMapService.deleteSeatMap(id);
        ApiResponse apiResponse = new ApiResponse("Seat Map deleted");
        return ResponseEntity.ok(apiResponse);

    }

}
