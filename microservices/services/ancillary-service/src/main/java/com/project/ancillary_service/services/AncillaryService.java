package com.project.ancillary_service.services;

import java.util.List;

import com.project.payload.request.AncillaryRequest;
import com.project.payload.response.AncillaryResponse;

public interface AncillaryService {

    AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request);

    AncillaryResponse getById(Long id) throws Exception;

    List<AncillaryResponse> getByAirlineId(Long airlineId);

    AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception;

    void deleteAncillary(Long id) throws Exception;

}
