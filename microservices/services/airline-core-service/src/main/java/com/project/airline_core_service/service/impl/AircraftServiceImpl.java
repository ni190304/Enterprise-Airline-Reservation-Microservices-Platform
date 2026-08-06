package com.project.airline_core_service.service.impl;

import com.project.airline_core_service.repository.AircraftRepository;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.airline_core_service.mapper.AircraftMapper;
import com.project.airline_core_service.model.Aircraft;
import com.project.airline_core_service.model.Airline;
import com.project.airline_core_service.repository.AirlineRepository;
import com.project.airline_core_service.service.AircraftService;
import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline does not exist for this owner id"));

        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())) {
            throw new Exception("Aircraft with this code already exists");
        }

        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("seating capacity cannot exceed to total seats");
        }

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + id));
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner id: " + ownerId));

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner id: " + ownerId));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());

        if (aircraft == null) {
            throw new Exception("Aircraft not found with id: " + id + " for owner id: " + ownerId);
        }

        if (aircraft.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCodeAndId(request.getCode(),id)) {
            throw new Exception("Aircraft with this code already exists");
        }

        AircraftMapper.toEntity(request, airline);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner id: " + ownerId));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());

        if (aircraft == null) {
            throw new Exception("Aircraft not found with id: " + id + " for owner id: " + ownerId);
        }

        aircraftRepository.delete(aircraft);

    }

}
