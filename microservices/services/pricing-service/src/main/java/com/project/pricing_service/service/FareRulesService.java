package com.project.pricing_service.service;

import java.util.List;

import com.project.payload.request.FareRulesRequest;
import com.project.payload.response.FareRulesResponse;

public interface FareRulesService {

    FareRulesResponse createFareRules(FareRulesRequest request) throws Exception;

    FareRulesResponse getFareRulesById(Long id) throws Exception;

    FareRulesResponse getFareRulesByFareId(Long fareId);

    List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId);

    FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception;

    void deleteFareRules(Long id);

}
