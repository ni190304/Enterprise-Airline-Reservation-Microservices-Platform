package com.project.seat_service.services;

import java.util.List;

import com.project.enums.CabinClassType;
import com.project.payload.request.CabinClassRequest;
import com.project.payload.response.CabinClassResponse;

public interface CabinClassService {

    CabinClassResponse createCabinClass(CabinClassRequest cabinClassRequest) throws Exception;

    CabinClassResponse getCabinClassById(Long id) throws Exception;

    List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId);

    CabinClassResponse getByAircraftIdAndName(Long aircraftId, CabinClassType name);

    CabinClassResponse updateCabinClass(Long id, CabinClassRequest cabinClassRequest) throws Exception;

    void deleteCabinClass(Long id) throws Exception;

}
