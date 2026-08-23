package com.project.ancillary_service.controller;

import java.util.List;

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

import com.project.ancillary_service.services.MealService;
import com.project.payload.request.MealRequest;
import com.project.payload.response.MealResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @Valid @RequestBody MealRequest mealRequest,
            @RequestHeader("X-Airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(

                        mealService.createMeal(airlineId, mealRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMealById(@PathVariable Long id)
            throws Exception {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<MealResponse>> getMealsByAirlineId(
            @RequestHeader("X-Airline-Id") Long airlineId) {
        return ResponseEntity.ok(mealService.getByAirlineId(airlineId));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<MealResponse> updateMealAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) throws Exception {
        return ResponseEntity.ok(mealService.updateAvailability(id, available));

    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponse> updateMeal(
            @PathVariable Long id,
            @RequestBody MealRequest request,
            @RequestHeader("X-Airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.ok(mealService.updateMeal(airlineId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) throws Exception {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }

}
