package com.project.ancillary_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ancillary_service.mapper.MealMapper;
import com.project.ancillary_service.model.Meal;
import com.project.ancillary_service.repository.MealRepository;
import com.project.ancillary_service.services.MealService;
import com.project.payload.request.MealRequest;
import com.project.payload.response.MealResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;

    @Override
    public MealResponse createMeal(Long airlineId, MealRequest request) throws Exception {
        if (mealRepository.existsByCodeAndAirlineId(request.getCode(), airlineId)) {
            throw new Exception("meal code already exist");
        }

        Meal meal = Meal.builder()
                .code(request.getCode())
                .name(request.getName())
                .mealType(request.getMealType())
                .dietaryRestriction(request.getDietaryRestriction())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .requiresAdvanceBooking(request.getRequiresAdvanceBooking() != null
                        ? request.getRequiresAdvanceBooking()
                        : false)
                .advanceBookingHours(request.getAdvanceBookingHours())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .airlineId(airlineId)
                .build();

        Meal saved = mealRepository.save(meal);

        return MealMapper.toResponse(saved);

    }

    @Override
    public MealResponse getMealById(Long id) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("meal not found with id")

        );

        return MealMapper.toResponse(meal);
    }

    @Override
    public MealResponse updateMeal(Long airlineId, Long id, MealRequest request) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("meal not found with id"));

        if (request.getCode() != null &&
                mealRepository.existsByAirlineIdAndCodeAndIdNot(airlineId, request.getCode(), meal.getId())) {
            throw new Exception("meal code already exist");
        }

        meal.setCode(request.getCode());
        meal.setName(request.getName());
        meal.setMealType(request.getMealType());
        meal.setDietaryRestriction(request.getDietaryRestriction());
        meal.setIngredients(request.getIngredients());
        meal.setImageUrl(request.getImageUrl());
        meal.setRequiresAdvanceBooking(request.getRequiresAdvanceBooking());
        meal.setAdvanceBookingHours(request.getAdvanceBookingHours());
        meal.setDisplayOrder(request.getDisplayOrder());

        Meal updated = mealRepository.save(meal);
        return MealMapper.toResponse(updated);

    }

    @Override
    public List<MealResponse> getByAirlineId(Long airlineId) {
        return mealRepository.findByAirlineId(airlineId)
                .stream().map(
                        MealMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMeal(Long id) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("meal not found with id"));

        mealRepository.delete(meal);
    }

    @Override
    public MealResponse updateAvailability(Long id, Boolean availability) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("meal not found with id"));
        meal.setAvailable(availability);
        Meal updated = mealRepository.save(meal);
        return MealMapper.toResponse(updated);

    }

}
