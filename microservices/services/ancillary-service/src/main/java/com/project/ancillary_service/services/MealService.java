package com.project.ancillary_service.services;

import java.util.List;

import com.project.payload.request.MealRequest;
import com.project.payload.response.MealResponse;

public interface MealService {

    MealResponse createMeal(Long userId, MealRequest request) throws Exception;

    MealResponse getMealById(Long id) throws Exception;

    MealResponse updateMeal(Long userId, Long id, MealRequest request) throws Exception;

    List<MealResponse> getByAirlineId(Long userId);

    void deleteMeal(Long id) throws Exception;

    MealResponse updateAvailability(Long id, Boolean availability) throws Exception;

}
