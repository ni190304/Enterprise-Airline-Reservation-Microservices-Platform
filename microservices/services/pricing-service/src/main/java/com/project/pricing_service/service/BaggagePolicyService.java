package com.project.pricing_service.service;

import java.util.List;

import com.project.payload.request.BaggagePolicyRequest;
import com.project.payload.response.BaggagePolicyResponse;

public interface BaggagePolicyService {

    BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception;

    BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception;

    BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId);

    List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId);

    BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception;

    void deleteBaggagePolicy(Long id);
}
