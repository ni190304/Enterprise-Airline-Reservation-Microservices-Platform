package com.project.ancillary_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ancillary_service.mapper.AncillaryMapper;
import com.project.ancillary_service.model.Ancillary;
import com.project.ancillary_service.repository.AncillaryRepository;
import com.project.ancillary_service.services.AncillaryService;
import com.project.payload.request.AncillaryRequest;
import com.project.payload.response.AncillaryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AncillaryServiceImpl implements AncillaryService {

    private final AncillaryRepository ancillaryRepository;

    @Override
    public AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request) {

        Ancillary ancillary = Ancillary.builder()
                .type(request.getType())
                .subType(request.getSubType())
                .rfisc(request.getRfisc())
                .name(request.getName())
                .description(request.getDescription())
                .metadata(request.getMetadata())
                .displayOrder(request.getDisplayOrder())
                .airlineId(airlineId)
                .build();

        Ancillary saved = ancillaryRepository.save(ancillary);
        return AncillaryMapper.toResponse(saved, null);
    }

    @Override
    public AncillaryResponse getById(Long id) throws Exception {

        Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Ancillary not found"));

        return AncillaryMapper.toResponse(ancillary, null);
    }

    @Override
    public List<AncillaryResponse> getByAirlineId(Long airlineId) {

        return ancillaryRepository.findByAirlineId(airlineId)
                .stream()
                .map(
                        ancillary -> {
                            // todo : fetch insurance coverages by ancillary
                            return AncillaryMapper.toResponse(ancillary, null);
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
        // todo : fetch insurance coverages by ancillary

        return AncillaryMapper.toResponse(updated, null);

    }

    @Override
    public void deleteAncillary(Long id) throws Exception {
        Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Ancillary not found"));

        ancillaryRepository.delete(ancillary);
    }

}
