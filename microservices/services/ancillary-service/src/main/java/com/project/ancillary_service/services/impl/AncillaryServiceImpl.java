package com.project.ancillary_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ancillary_service.client.AirlineClient;
import com.project.ancillary_service.mapper.AncillaryMapper;
import com.project.ancillary_service.mapper.InsuranceCoverageMapper;
import com.project.ancillary_service.model.Ancillary;
import com.project.ancillary_service.model.InsuranceCoverage;
import com.project.ancillary_service.repository.AncillaryRepository;
import com.project.ancillary_service.repository.InsuranceCoverageRepository;
import com.project.ancillary_service.services.AncillaryService;
import com.project.payload.request.AncillaryRequest;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AncillaryResponse;
import com.project.payload.response.InsuranceCoverageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AncillaryServiceImpl implements AncillaryService {

        private final AncillaryRepository ancillaryRepository;
        private final InsuranceCoverageRepository insuranceCoverageRepository;
        private final AirlineClient airlineClient;

        @Override
        public AncillaryResponse createAncillary(Long userId, AncillaryRequest request) {

                AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

                Ancillary ancillary = Ancillary.builder()
                                .type(request.getType())
                                .subType(request.getSubType())
                                .rfisc(request.getRfisc())
                                .name(request.getName())
                                .description(request.getDescription())
                                .metadata(request.getMetadata())
                                .displayOrder(request.getDisplayOrder())
                                .airlineId(airlineResponse.getOwnerId())
                                .build();

                Ancillary saved = ancillaryRepository.save(ancillary);
                return AncillaryMapper.toResponse(saved, null);
        }

        @Override
        public AncillaryResponse getById(Long id) throws Exception {

                Ancillary ancillary = ancillaryRepository.findById(id)
                                .orElseThrow(
                                                () -> new Exception("Ancillary not found"));

                List<InsuranceCoverage> coverages = insuranceCoverageRepository
                                .findByAncillaryId(ancillary.getId());

                List<InsuranceCoverageResponse> coverageResponses = coverages.stream()
                                .map(InsuranceCoverageMapper::toResponse)
                                .toList();

                return AncillaryMapper.toResponse(ancillary, coverageResponses);
        }

        @Override
        public List<AncillaryResponse> getByAirlineId(Long userId) {

                AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

                return ancillaryRepository.findByAirlineId(airlineResponse.getId())
                                .stream()
                                .map(
                                                ancillary -> {
                                                        // todo : fetch insurance coverages by ancillary
                                                        List<InsuranceCoverage> coverages = insuranceCoverageRepository
                                                                        .findByAncillaryId(ancillary.getId());

                                                        List<InsuranceCoverageResponse> coverageResponses = coverages
                                                                        .stream()
                                                                        .map(InsuranceCoverageMapper::toResponse)
                                                                        .toList();

                                                        return AncillaryMapper.toResponse(ancillary, coverageResponses);
                                                })
                                .collect(Collectors.toList());
        }

        @Override
        public AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception {
                Ancillary ancillary = ancillaryRepository.findById(id)
                                .orElseThrow(
                                                () -> new Exception("Ancillary not found"));

                ancillary.setType(request.getType());
                ancillary.setSubType(request.getSubType());
                ancillary.setRfisc(request.getRfisc());
                ancillary.setName(request.getName());
                ancillary.setDescription(request.getDescription());
                ancillary.setMetadata(request.getMetadata());
                ancillary.setDisplayOrder(request.getDisplayOrder());

                Ancillary updated = ancillaryRepository.save(ancillary);

                List<InsuranceCoverage> coverages = insuranceCoverageRepository
                                .findByAncillaryId(ancillary.getId());

                List<InsuranceCoverageResponse> coverageResponses = coverages.stream()
                                .map(InsuranceCoverageMapper::toResponse)
                                .toList();

                return AncillaryMapper.toResponse(updated, coverageResponses);

        }

        @Override
        public void deleteAncillary(Long id) throws Exception {
                Ancillary ancillary = ancillaryRepository.findById(id)
                                .orElseThrow(
                                                () -> new Exception("Ancillary not found"));

                ancillaryRepository.delete(ancillary);
        }

}
