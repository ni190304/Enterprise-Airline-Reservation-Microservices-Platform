package com.project.pricing_service.service;

import java.util.List;
import java.util.Map;

import com.project.payload.request.FareRequest;
import com.project.payload.response.FareResponse;
import com.project.pricing_service.model.Fare;

public interface FareService {

    FareResponse createFare(FareRequest request) throws Exception;

    FareResponse getFareById(Long id) throws Exception;

    List<FareResponse> getFaresByFlightIdAndCabinClassId(
            Long flightId, Long cabinClassId);

    FareResponse updateFare(Long id, FareRequest request);

    void deleteFare(Long id) throws Exception;

    List<Fare> getFares();

    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId
    );

    Map<Long,FareResponse> getFaresByIds(List<Long> ids);
}
