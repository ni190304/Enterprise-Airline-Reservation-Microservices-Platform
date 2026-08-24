package com.project.ancillary_service.services;

import java.util.List;

import com.project.payload.request.FlightMealRequest;
import com.project.payload.response.FlightMealResponse;

public interface FlightMealService {

    FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception;

    FlightMealResponse getFlightMealById(Long id) throws Exception;

    List<FlightMealResponse> getByFlightId(Long flightId);

    List<FlightMealResponse> getAllByIds(List<Long> ids);

    FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception;

    void deleteFlightMeal(Long id) throws Exception;

    FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws Exception;

    Double calculateMealPrice(List<Long> mealIds);

}
