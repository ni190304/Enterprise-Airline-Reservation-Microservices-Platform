package com.project.ancillary_service.services;

import java.util.List;

import com.project.payload.request.MealRequest;
import com.project.payload.response.MealResponse;

public interface MealService {

    MealResponse createMeal(Long airlineId, MealRequest request) throws Exception;

    MealResponse getMealById(Long id) throws Exception;

    MealResponse updateMeal(Long airlineId, Long id, MealRequest request) throws Exception;

    List<MealResponse> getByAirlineId(Long airlineId);

    void deleteMeal(Long id) throws Exception;

    MealResponse updateAvailability(Long id, Boolean availability) throws Exception;

}
