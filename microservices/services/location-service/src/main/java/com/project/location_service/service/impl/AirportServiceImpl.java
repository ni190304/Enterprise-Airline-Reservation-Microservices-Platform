package com.project.location_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.location_service.repository.AirportRepository;
import com.project.location_service.repository.CityRepository;
import com.project.location_service.service.AirportService;
import com.project.payload.request.AirportRequest;
import com.project.payload.response.AirportResponse;

import lombok.RequiredArgsConstructor;

import com.project.location_service.mapper.AirportMapper;
import com.project.location_service.model.Airport;
import com.project.location_service.model.City;;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {

        if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with Iata Code already exists");
        }

        City city = cityRepository.findById(request.getCityId()).orElseThrow(
                () -> new Exception("City not found"));

        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return AirportMapper.toResponse(savedAirport);

    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {

        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exists with provided id"));

        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exists with id: " + id));

        if (request.getIataCode() != null
                && !existingAirport.getIataCode().equals(request.getIataCode())
                && airportRepository.findByIataCode(request.getIataCode()).isPresent()) {

            throw new Exception("Airport with Iata Code Already exists");

        }

        AirportMapper.updateEntity(existingAirport, request);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);

    }

    @Override
    public void deleteAirport(Long id) throws Exception {

        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with provided id"));

        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

}
