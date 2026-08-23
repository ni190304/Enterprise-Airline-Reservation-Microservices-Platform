package com.project.ancillary_service.services;

import java.util.List;

import com.project.payload.request.InsuranceCoverageRequest;
import com.project.payload.response.InsuranceCoverageResponse;

public interface InsuranceCoverageService {

    InsuranceCoverageResponse createCoverage(InsuranceCoverageRequest request) throws Exception;

    InsuranceCoverageResponse updateCoverage(Long id, InsuranceCoverageRequest request) throws Exception;

    void deleteCoverage(Long id) throws Exception;

    InsuranceCoverageResponse getCoverage(Long id) throws Exception;

    List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId);

    List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId);

    List<InsuranceCoverageResponse> getAllCoverages();

}
