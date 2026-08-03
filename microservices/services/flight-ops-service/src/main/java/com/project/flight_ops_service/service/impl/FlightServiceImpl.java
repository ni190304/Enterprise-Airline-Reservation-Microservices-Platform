package com.project.flight_ops_service.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.enums.FlightStatus;
import com.project.flight_ops_service.mapper.FlightMapper;
import com.project.flight_ops_service.model.Flight;
import com.project.flight_ops_service.repository.FlightRepository;
import com.project.flight_ops_service.service.FlightService;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightResponse;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository = null;

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
    public FlightResponse getFlightById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFlightById'");
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFlight'");
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeStatus'");
    }

    @Override
    public void deleteFlight(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFlight'");
    }

    @Override
    public FlightResponse convertToFlightResponse(Flight flight) {

        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();

        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return FlightMapper.toResponse(flight, aircraft, airline, departureAirport, arrivalAirport);
    }

}
