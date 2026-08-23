package com.project.ancillary_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ancillary_service.mapper.InsuranceCoverageMapper;
import com.project.ancillary_service.model.Ancillary;
import com.project.ancillary_service.model.InsuranceCoverage;
import com.project.ancillary_service.repository.AncillaryRepository;
import com.project.ancillary_service.repository.InsuranceCoverageRepository;
import com.project.ancillary_service.services.InsuranceCoverageService;
import com.project.payload.request.InsuranceCoverageRequest;
import com.project.payload.response.InsuranceCoverageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {

    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public InsuranceCoverageResponse createCoverage(InsuranceCoverageRequest request) throws Exception {
        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(
                        () -> new Exception("ancillary not found with provided id"));
        InsuranceCoverage coverage = InsuranceCoverageMapper.toEntity(request, ancillary);

        InsuranceCoverage saved = insuranceCoverageRepository.save(coverage);

        return InsuranceCoverageMapper.toResponse(saved);
    }

    @Override
    public InsuranceCoverageResponse updateCoverage(Long id, InsuranceCoverageRequest request) throws Exception {
        InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(
                id).orElseThrow(
                        () -> new Exception("insurance coverage not found with id"));
        Ancillary ancillary = null;
        if (request.getAncillaryId() != null) {
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(
                            () -> new Exception("ancillary not found with provided id"));
        }

        InsuranceCoverageMapper.updateEntityFromRequest(insuranceCoverage, request, ancillary);
        InsuranceCoverage saved = insuranceCoverageRepository.save(insuranceCoverage);
        return InsuranceCoverageMapper.toResponse(saved);
    }

    @Override
    public void deleteCoverage(Long id) throws Exception {
        InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(
                id).orElseThrow(
                        () -> new Exception("insurance coverage not found with id"));

        insuranceCoverageRepository.delete(insuranceCoverage);
    }

    @Override
    public InsuranceCoverageResponse getCoverage(Long id) throws Exception {

        InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(
                id).orElseThrow(
                        () -> new Exception("insurance coverage not found with id"));

        return InsuranceCoverageMapper.toResponse(insuranceCoverage);
    }

    @Override
    public List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId) {
        return insuranceCoverageRepository.findByAncillaryId(ancillaryId).stream()
                .map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId) {
        return insuranceCoverageRepository.findByAncillaryIdAndActiveTrue(ancillaryId).stream()
                .map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCoverageResponse> getAllCoverages() {
        return insuranceCoverageRepository.findAll().stream()
                .map(InsuranceCoverageMapper::toResponse).collect(Collectors.toList());
    }

}
