package com.project.pricing_service.service.impl;

import java.util.List;
import java.util.Map;

import com.project.payload.request.FareRequest;
import com.project.payload.response.FareResponse;
import com.project.pricing_service.model.Fare;
import com.project.pricing_service.service.FareService;

public class FareServiceImpl implements FareService {

    @Override
    public FareResponse createFare(FareRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFare'");
    }

    @Override
    public FareResponse getFareById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFareById'");
    }

    @Override
    public List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFaresByFlightIdAndCabinClassId'");
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFare'");
    }

    @Override
    public void deleteFare(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFare'");
    }

    @Override
    public List<Fare> getFares() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFares'");
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLowestFarePerFlight'");
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFaresByIds'");
    }

}
