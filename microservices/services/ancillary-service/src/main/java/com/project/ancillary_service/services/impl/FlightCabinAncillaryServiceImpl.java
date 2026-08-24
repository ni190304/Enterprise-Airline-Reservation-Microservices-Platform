package com.project.ancillary_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ancillary_service.mapper.FlightCabinAncillaryMapper;
import com.project.ancillary_service.mapper.InsuranceCoverageMapper;
import com.project.ancillary_service.model.Ancillary;
import com.project.ancillary_service.model.FlightCabinAncillary;
import com.project.ancillary_service.model.InsuranceCoverage;
import com.project.ancillary_service.repository.AncillaryRepository;
import com.project.ancillary_service.repository.FlightCabinAncillaryRepository;
import com.project.ancillary_service.repository.InsuranceCoverageRepository;
import com.project.ancillary_service.services.FlightCabinAncillaryRequest;
import com.project.ancillary_service.services.FlightCabinAncillaryResponse;
import com.project.ancillary_service.services.FlightCabinAncillaryService;
import com.project.enums.AncillaryType;
import com.project.payload.response.InsuranceCoverageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {

    private final FlightCabinAncillaryRepository flightCabinAncillaryRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request) throws Exception {

        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(() -> new Exception("ancillary not found"));

        FlightCabinAncillary fCabinAncillary = FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();

        FlightCabinAncillary saved = flightCabinAncillaryRepository.save(fCabinAncillary);
        return convertToResponse(saved);

    }

    @Override
    public FlightCabinAncillaryResponse getById(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("flight cabin ancillary not found"));
        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return flightCabinAncillaryRepository.findByFlightIdAndCabinClassId(
                flightId, cabinClassId).stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return flightCabinAncillaryRepository.findAllById(ids)
                .stream()
                .map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId,
            AncillaryType type) {
        FlightCabinAncillary flightCabinAncillaryResponse = flightCabinAncillaryRepository
                .findByFlightIdAndCabinClassIdAndAncillary_Type(
                        flightId, cabinClassId, type);

        return convertToResponse(flightCabinAncillaryResponse);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId,
            AncillaryType type) {

        return flightCabinAncillaryRepository.findAllByFlightIdAndCabinClassIdAndAncillaryType(
                flightId, cabinClassId, type).stream().map(
                        this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request) throws Exception {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(
                id).orElseThrow(() -> new Exception("Flight cabin ancillary not found"));

        flightCabinAncillary.setAvailable(request.getAvailable());
        flightCabinAncillary.setMaxQuantity(request.getMaxQuantity());
        flightCabinAncillary.setPrice(request.getPrice());
        flightCabinAncillary.setIncludedInFare(request.getIncludedInFare());

        FlightCabinAncillary saved = flightCabinAncillaryRepository.save(flightCabinAncillary);
        return convertToResponse(saved);

    }

    @Override
    public void delete(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(
                id).orElseThrow(() -> new Exception("Flight cabin ancillary not found"));
        flightCabinAncillaryRepository.delete(flightCabinAncillary);
    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        List<FlightCabinAncillary> ancillaries = flightCabinAncillaryRepository.findAllById(ancillaryIds);
        double totalPrice = 0;
        for (FlightCabinAncillary ancillary : ancillaries) {
            totalPrice += ancillary.getPrice();
        }
        return totalPrice;
    }

    private FlightCabinAncillaryResponse convertToResponse(FlightCabinAncillary ancillary) {
        List<InsuranceCoverage> coverages = insuranceCoverageRepository
                .findByAncillaryId(ancillary.getId());
        List<InsuranceCoverageResponse> coverageResponses = coverages.stream().map(
                InsuranceCoverageMapper::toResponse).toList();
        return FlightCabinAncillaryMapper.toResponse(ancillary, coverageResponses);
    }
}
