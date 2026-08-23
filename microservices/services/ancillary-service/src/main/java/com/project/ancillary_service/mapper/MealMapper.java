package com.project.ancillary_service.mapper;

import com.project.ancillary_service.model.Meal;
import com.project.payload.response.MealResponse;

public class MealMapper {

    public static MealResponse toResponse(Meal meal) {
        if (meal == null)
            return null;

        return MealResponse.builder()
                .id(meal.getId())
                .name(meal.getName())
                .mealType(meal.getMealType())
                .dietaryRestriction(meal.getDietaryRestriction())
                .ingredients(meal.getIngredients())
                .imageUrl(meal.getImageUrl())
                .available(meal.getAvailable())
                .requiresAdvanceBooking(meal.getRequiresAdvanceBooking())
                .advanceBookingHours(meal.getAdvanceBookingHours())
                .displayOrder(meal.getDisplayOrder())
                .airlineId(meal.getAirlineId())
                .createdAt(meal.getCreatedAt())
                .updatedAt(meal.getUpdatedAt())
                .build();
    }

}
