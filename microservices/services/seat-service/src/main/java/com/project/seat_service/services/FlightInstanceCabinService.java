package com.project.seat_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.payload.request.FlightInstanceCabinRequest;
import com.project.payload.response.FlightInstanceCabinResponse;

public interface FlightInstanceCabinService {

    FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception;

    FlightInstanceCabinResponse getFlightInstanceCabinById(Long id);

    Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long id, Pageable pageable);

    FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId);

    FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request);

    void deleteFlightInstanceCabin(Long id);

}
