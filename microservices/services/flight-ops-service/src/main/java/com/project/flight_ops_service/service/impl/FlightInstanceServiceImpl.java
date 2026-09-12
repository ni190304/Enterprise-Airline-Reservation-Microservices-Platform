package com.project.flight_ops_service.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.flight_ops_service.client.AirlineClient;
import com.project.flight_ops_service.client.LocationClient;
import com.project.flight_ops_service.mapper.FlightInstanceMapper;
import com.project.flight_ops_service.model.Flight;
import com.project.flight_ops_service.model.FlightInstance;
import com.project.flight_ops_service.repository.FlightInstanceRepository;
import com.project.flight_ops_service.repository.FlightRepository;
import com.project.flight_ops_service.service.FlightInstanceService;
import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightInstanceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

        private final FlightRepository flightRepository;
        private final FlightInstanceRepository flightInstanceRepository;
        private final AirlineClient airlineClient;
        private final LocationClient locationClient;

        @Override
        public FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request)
                        throws Exception {

                 AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

                Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                                () -> new Exception("Flight not found"));

                AircraftResponse aircraft = airlineClient.getAircraftById(flight.getAircraftId());

                FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
                flightInstance.setTotalSeats(aircraft.getTotalSeats());
                flightInstance.setAvailableSeats(aircraft.getTotalSeats());

                FlightInstance saved = flightInstanceRepository.save(flightInstance);

                // publish kafka event , seat service consume that and create seat instance

                return convertToFlightInstanceResponse(saved);

        }

        @Override
        public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {

                FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                                () -> new Exception("Flight instance not found with " + id));

                return convertToFlightInstanceResponse(flightInstance);

        }

        @Override
        public Page<FlightInstanceResponse> getByAirlineId(Long userId, Long departureAirportId,
                        Long arrivalAirportId,
                        Long flightId, LocalDate onDate, Pageable pageable) {

                 AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

                LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
                LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;

                return flightInstanceRepository.findByAirlineId(airlineResponse.getId(), departureAirportId, arrivalAirportId,
                                flightId, start, end, pageable).map(this::convertToFlightInstanceResponse);

        }

        @Override
        public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {

                FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                                () -> new Exception("Flight instance not found with " + id));

                FlightInstanceMapper.updateEntity(request, existing);

                return convertToFlightInstanceResponse(flightInstanceRepository.save(existing));

        }

        @Override
        public void deleteFlightInstance(Long id) throws Exception {

                FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                                () -> new Exception("Flight instance not found with " + id));

                flightInstanceRepository.delete(existing);

        }

        private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {

                AirlineResponse airline = airlineClient.getAirlineById(flightInstance.getAirlineId());
                AirportResponse departureAirport = locationClient.getAirportById(flightInstance.getDepartureAirportId());
                AirportResponse arrivalAirport = locationClient.getAirportById(flightInstance.getArrivalAirportId());
                AircraftResponse aircraftResponse = airlineClient.getAircraftById(flightInstance.getFlight().getAircraftId());

                return FlightInstanceMapper.toResponse(
                                flightInstance,
                                aircraftResponse,
                                airline,
                                departureAirport,
                                arrivalAirport);
        }

}
