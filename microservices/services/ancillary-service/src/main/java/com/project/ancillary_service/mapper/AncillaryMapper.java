package com.project.ancillary_service.mapper;

import java.util.List;

import com.project.ancillary_service.model.Ancillary;
import com.project.payload.response.AncillaryResponse;
import com.project.payload.response.InsuranceCoverageResponse;

public class AncillaryMapper {

    public static AncillaryResponse toResponse(
            Ancillary ancillary,
            List<InsuranceCoverageResponse> coverageResponsesList) {
        if (ancillary == null)
            return null;

        return AncillaryResponse.builder()
                .id(ancillary.getId())
                .type(ancillary.getType())
                .subType(ancillary.getSubType())
                .rfisc(ancillary.getRfisc())
                .name(ancillary.getName())
                .description(ancillary.getDescription())
                .metadata(ancillary.getMetadata())
                .coverages(coverageResponsesList)
                .displayOrder(ancillary.getDisplayOrder())
                .airlineId(ancillary.getAirlineId())
                .build();

    }
}
