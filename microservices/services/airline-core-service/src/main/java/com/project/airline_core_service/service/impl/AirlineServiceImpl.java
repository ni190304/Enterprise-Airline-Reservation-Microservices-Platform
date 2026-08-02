package com.project.airline_core_service.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.airline_core_service.mapper.AirlineMapper;
import com.project.airline_core_service.model.Airline;
import com.project.airline_core_service.repository.AirlineRepository;
import com.project.airline_core_service.service.AirlineService;
import com.project.enums.AirlineStatus;
import com.project.payload.request.AirlineRequest;
import com.project.payload.response.AirlineDropdownItem;
import com.project.payload.response.AirlineResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(request, ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId " + ownerId));

        return AirlineMapper.toResponse(airline);

    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new Exception("Airline not found with id " + id));

        return AirlineMapper.toResponse(airline);
    }

    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(
                AirlineMapper::toResponse);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId " + ownerId));

        AirlineMapper.updateEntity(airline, request);

        Airline savedAirline = airlineRepository.save(airline);

        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId " + ownerId));

        airlineRepository.delete(airline);

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new Exception("Airline not found with id " + airlineId));

        airline.setStatus(status);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {

        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(
                        a -> AirlineDropdownItem.builder()
                                .id(a.getId())
                                .name(a.getName())
                                .iataCode(a.getIataCode())
                                .icaoCode(a.getIcaoCode())
                                .logoUrl(a.getLogoUrl())
                                .build())
                .toList();
    }

}
