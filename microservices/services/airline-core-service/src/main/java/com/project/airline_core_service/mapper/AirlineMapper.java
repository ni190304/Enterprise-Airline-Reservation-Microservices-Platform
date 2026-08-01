package com.project.airline_core_service.mapper;

import com.project.airline_core_service.model.Airline;
import com.project.payload.request.AirlineRequest;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.Support;

public class AirlineMapper {

    public static Airline toEntity(AirlineRequest request, Long ownerId) {

        if (request == null)
            return null;

        Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .status(request.getStatus())
                .alliance(request.getAlliance())
                .headquartersCityId(request.getHeadqueartersCityId())
                .ownerId(ownerId)
                .build();

        if (request.getSupportEmail() != null
                || request.getSupportPhone() != null
                || request.getSupportHours() != null) {

            airline.setSupport(

                    Support.builder()
                            .email(request.getSupportEmail())
                            .phone(request.getSupportPhone())
                            .hours(request.getSupportHours())
                            .build()

            );

        }

        return airline;
    }

    public static AirlineResponse toResponse(Airline airline) {

        if (airline == null)
            return null;

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .support(airline.getSupport())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .build();
    }

    public static void updateEntity(Airline airline, AirlineRequest request) {

        airline.setIataCode(request.getIataCode());
        airline.setIcaoCode(request.getIcaoCode());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        airline.setStatus(request.getStatus());
        airline.setAlliance(request.getAlliance());
        airline.setHeadquartersCityId(request.getHeadqueartersCityId());

        if (airline.getSupport() == null) {
            airline.setSupport((new Support()));
        }

        airline.getSupport().setEmail(request.getSupportEmail());
        airline.getSupport().setPhone(request.getSupportPhone());
        airline.getSupport().setHours(request.getSupportHours());
    }

}
