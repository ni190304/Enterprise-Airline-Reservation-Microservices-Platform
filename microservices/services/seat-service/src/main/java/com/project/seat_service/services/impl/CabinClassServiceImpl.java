package com.project.seat_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.enums.CabinClassType;
import com.project.payload.request.CabinClassRequest;
import com.project.payload.response.CabinClassResponse;
import com.project.seat_service.mapper.CabinClassMapper;
import com.project.seat_service.model.CabinClass;
import com.project.seat_service.repository.CabinClassRepository;
import com.project.seat_service.services.CabinClassService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CabinClassServiceImpl implements CabinClassService {

    private final CabinClassRepository cabinClassRepository;

    @Override
    public CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception {

        if (cabinClassRepository.existsByCodeAndAircraftId(request.getCode(), request.getAircraftId())) {
            throw new Exception("cabin class with code already exists");
        }

        CabinClass cabinClass = CabinClassMapper.toEntity(request);
        CabinClass savedCabin = cabinClassRepository.save(cabinClass);

        return CabinClassMapper.toResponse(savedCabin);
    }

    @Override
    public CabinClassResponse getCabinClassById(Long id) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(id).orElseThrow(
                () -> new Exception("cabin class not found with id"));

        return CabinClassMapper.toResponse(cabinClass);

    }

    @Override
    public List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId) {
        return cabinClassRepository.findByAircraftId(aircraftId)
                .stream()
                .map(CabinClassMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CabinClassResponse getByAircraftIdAndName(Long aircraftId, CabinClassType name) {

        CabinClass cabinClass = cabinClassRepository.findByAircraftIdAndName(aircraftId, name);

        return CabinClassMapper.toResponse(cabinClass);

    }

    @Override
    public CabinClassResponse updateCabinClass(Long id, CabinClassRequest cabinClassRequest) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(id).orElseThrow(
                () -> new Exception("cabin class not found with id"));

        if (cabinClassRepository.existsByCodeAndAircraftIdAndIdNot(
                cabinClassRequest.getCode().toUpperCase(),
                cabinClass.getAircraftId(),
                cabinClass.getId())) {

            throw new Exception("cabin class with code already exist");
        }

        CabinClassMapper.updateEntity(cabinClassRequest, cabinClass);
        CabinClass updated = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toResponse(updated);
    }

    @Override
    public void deleteCabinClass(Long id) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(id).orElseThrow(
                () -> new Exception("cabin class not found with id"));

        cabinClassRepository.delete(cabinClass);

    }

}
