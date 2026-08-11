package com.project.pricing_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.payload.request.FareRulesRequest;
import com.project.payload.response.FareRulesResponse;
import com.project.pricing_service.mapper.FareRuleMapper;
import com.project.pricing_service.model.Fare;
import com.project.pricing_service.model.FareRules;
import com.project.pricing_service.repository.FareRepository;
import com.project.pricing_service.repository.FareRulesRepository;
import com.project.pricing_service.service.FareRulesService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FareRulesServiceImpl implements FareRulesService {

    private final FareRepository fareRepository;
    private final FareRulesRepository fareRulesRepository;

    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new Exception("Fare not found"));

        if (fareRulesRepository.existsByFareId(fare.getId())) {
            throw new Exception("Fare already exists");
        }

        FareRules fareRules = FareRuleMapper.toEntity(request, fare);
        FareRules savedFaveRules = fareRulesRepository.save(fareRules);

        return FareRuleMapper.toResponse(savedFaveRules);
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("fare rule not found"));

        return FareRuleMapper.toResponse(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) {

        FareRules fareRules = fareRulesRepository.findByFareId(fareId);

        return FareRuleMapper.toResponse(fareRules);
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        return fareRulesRepository.findByAirlineId(airlineId).stream()
                .map(FareRuleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception {

        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("fare rule not found"));

        FareRuleMapper.updateEntity(request, fareRules);
        FareRules savedFareRules = fareRulesRepository.save(fareRules);
        return FareRuleMapper.toResponse(savedFareRules);
    }

    @Override
    public void deleteFareRules(Long id) {
        FareRules fareRules = fareRulesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare rules not found with id: " + id));
        fareRulesRepository.delete(fareRules);
    }

}
