package com.project.airline_core_service.service;

import java.util.List;

import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest request , Long ownerId) throws Exception;

    AircraftResponse getAircraftById(Long id);

    List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception;

    void deleteAircraft(Long id, Long ownerId) throws Exception;

    AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception;

}
