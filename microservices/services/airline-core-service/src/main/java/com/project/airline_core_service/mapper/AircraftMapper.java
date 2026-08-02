package com.project.airline_core_service.mapper;

import com.project.airline_core_service.model.Aircraft;
import com.project.airline_core_service.model.Airline;
import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequest request, Airline airline) {

        if (request == null)
            return null;

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintainceDate(request.getNextMaintenanceDate())
                .status(request.getStatus())
                .isAvailable(request.getIsAvailable())
                .airline(airline)
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft) {

        if (aircraft == null)
            return null;

        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintainceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .airlineId(aircraft.getAirline().getId())
                .airlineName(aircraft.getAirline().getName())
                .airlineIataCode(aircraft.getAirline().getIataCode())
                .currentAirportId(aircraft.getCurrentAirportId())
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.requiredMaintainence())
                .isOperational(aircraft.isOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Aircraft aircraft, AircraftRequest request, Airline airline) {

        aircraft.setCode(request.getCode());
        aircraft.setModel(request.getModel());
        aircraft.setManufacturer(request.getManufacturer());
        aircraft.setSeatingCapacity(request.getSeatingCapacity());
        aircraft.setEconomySeats(request.getEconomySeats());
        aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        aircraft.setBusinessSeats(request.getBusinessSeats());
        aircraft.setFirstClassSeats(request.getFirstClassSeats());
        aircraft.setRangeKm(request.getRangeKm());
        aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        aircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
        aircraft.setYearOfManufacture(request.getYearOfManufacture());
        aircraft.setRegistrationDate(request.getRegistrationDate());
        aircraft.setNextMaintainceDate(request.getNextMaintenanceDate());
        aircraft.setStatus(request.getStatus());
        aircraft.setIsAvailable(request.getIsAvailable());
        aircraft.setCurrentAirportId(request.getCurrentAirportId());
        aircraft.setAirline(airline);
    }

}
