package com.project.flight_ops_service.service;

import java.util.List;

import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.FlightScheduleResponse;

public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception;

    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;

    List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId);

    FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception;

    void deleteFlightSchedule(Long id) throws Exception;

}
