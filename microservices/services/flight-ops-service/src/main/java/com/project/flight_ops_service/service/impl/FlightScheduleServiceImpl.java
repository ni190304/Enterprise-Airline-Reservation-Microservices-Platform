package com.project.flight_ops_service.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.enums.FlightStatus;
import com.project.flight_ops_service.client.LocationClient;
import com.project.flight_ops_service.mapper.FlightScheduleMapper;
import com.project.flight_ops_service.model.Flight;
import com.project.flight_ops_service.model.FlightSchedule;
import com.project.flight_ops_service.repository.FlightRepository;
import com.project.flight_ops_service.repository.FlightScheduleRepository;
import com.project.flight_ops_service.service.FlightInstanceService;
import com.project.flight_ops_service.service.FlightScheduleService;
import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

        private final FlightRepository flightRepository;
        private final FlightScheduleRepository flightScheduleRepository;
        private final FlightInstanceService flightInstanceService;
        // private final AirlineClient airlineClient;
        private final LocationClient locationClient;

        @Override
        public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request)
                        throws Exception {

                Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                                () -> new Exception("flight not found with given id"));

                if (request.getEndDate().isBefore(request.getStartDate())) {
                        throw new Exception("end date is before start date");
                }

                FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
                FlightSchedule savedSchedule = flightScheduleRepository.save(flightSchedule);

                List<DayOfWeek> operatingDays = savedSchedule.getOperatingDays();
                LocalDate startDate = savedSchedule.getStartDate();
                LocalDate endDate = savedSchedule.getEndDate();

                FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                                .scheduleId(savedSchedule.getId())
                                .flightId(flight.getId())
                                .arrivalAirportId(flight.getArrivalAirportId())
                                .departureAirportId(flight.getDepartureAirportId())
                                .status(FlightStatus.SCHEDULED)
                                .build();

                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

                        if (operatingDays.contains(date.getDayOfWeek())) {

                                flightInstanceRequest.setDepartureDateTime(
                                                LocalDateTime.of(date, savedSchedule.getDepartureTime()));
                                flightInstanceRequest.setArrivalDateTime(
                                                LocalDateTime.of(date, savedSchedule.getArrivalTime()));
                                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
                        }

                }

                return convertToFlightScheduleResponse(savedSchedule);

        }

        @Override
        public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
                FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                                () -> new Exception("flight schedule not found"));

                return convertToFlightScheduleResponse(flightSchedule);
        }

        @Override
        public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) {

                List<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(airlineId);

                return schedules.stream().map(
                                this::convertToFlightScheduleResponse).toList();

        }

        @Override
        public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {

                FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                                () -> new Exception("flight schedule not found"));

                FlightScheduleMapper.updateEntity(request, flightSchedule);
                FlightSchedule updatedSchedule = flightScheduleRepository.save(flightSchedule);

                return convertToFlightScheduleResponse(updatedSchedule);

        }

        @Override
        public void deleteFlightSchedule(Long id) throws Exception {
                FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                                () -> new Exception("flight schedule not found"));

                flightScheduleRepository.delete(flightSchedule);
        }

        private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule) {
                AirportResponse departureAirport = locationClient
                                .getAirportById(flightSchedule.getDepartureAirportId());
                AirportResponse arrivalAirport = locationClient.getAirportById(flightSchedule.getArrivalAirportId());
                return FlightScheduleMapper.toResponse(
                                flightSchedule, arrivalAirport, departureAirport);
        }

}
