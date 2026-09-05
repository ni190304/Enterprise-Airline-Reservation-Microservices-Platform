package com.project.flight_ops_service.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.enums.FlightStatus;
import com.project.flight_ops_service.client.AirlineClient;
import com.project.flight_ops_service.client.LocationClient;
import com.project.flight_ops_service.mapper.FlightMapper;
import com.project.flight_ops_service.model.Flight;
import com.project.flight_ops_service.repository.FlightRepository;

import com.project.flight_ops_service.service.FlightService;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineClient airlineClient;
    private final LocationClient locationClient;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {

        if (flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new Exception("flight with id already exists");
        }

        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight saved = flightRepository.save(flight);

        return convertToFlightResponse(saved);

    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId,
            Pageable pageable) {

        return flightRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, pageable)
                .map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with the id" + id));

        return convertToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with the id" + id));

        if (flightRequest.getFlightNumber() != null
                && flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)) {
            throw new Exception("flight with id already exist.");
        }

        FlightMapper.updateEntity(flightRequest, existing);
        Flight updated = FlightMapper.toEntity(flightRequest);
        return convertToFlightResponse(updated);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with the id" + id));

        existing.setStatus(status);
        Flight updated = flightRepository.save(existing);
        return convertToFlightResponse(updated);

    }

    @Override
    public void deleteFlight(Long airlineId, Long id) throws Exception {
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId, id).orElseThrow(
                () -> new Exception("flight not found with the id" + id));

        flightRepository.delete(existing);
    }

    @Override
    public FlightResponse convertToFlightResponse(Flight flight) {

        AircraftResponse aircraft = airlineClient.getAircraftById(flight.getAircraftId());

        AirlineResponse airline = airlineClient.getAirlineById(flight.getAirlineId());

        AirportResponse departureAirport = locationClient.getAirportById(flight.getDepartureAirportId());

        AirportResponse arrivalAirport = locationClient.getAirportById(flight.getArrivalAirportId());

        return FlightMapper.toResponse(flight, aircraft, airline, departureAirport, arrivalAirport);
    }

}
