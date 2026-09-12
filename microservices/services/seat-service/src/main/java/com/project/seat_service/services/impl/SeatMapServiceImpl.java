package com.project.seat_service.services.impl;

import org.springframework.stereotype.Service;

import com.project.payload.request.SeatMapRequest;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.SeatMapResponse;
import com.project.seat_service.client.AirlineClient;
import com.project.seat_service.mapper.SeatMapMapper;
import com.project.seat_service.model.CabinClass;
import com.project.seat_service.model.SeatMap;
import com.project.seat_service.repository.CabinClassRepository;
import com.project.seat_service.repository.SeatMapRepository;
import com.project.seat_service.services.SeatMapService;
import com.project.seat_service.services.SeatService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatMapServiceImpl implements SeatMapService {

    private final SeatMapRepository seatMapRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatService seatService;
    private final AirlineClient airlineClient;

    @Override
    public SeatMapResponse createSeatMap(Long userId, SeatMapRequest request) throws Exception {

         AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);

        CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId()).orElseThrow(
                () -> new Exception("cabin class not found with given id"));

        if (seatMapRepository.existsByAirlineIdAndCabinClassIdAndName(
                airlineResponse.getId(), request.getCabinClassId(), request.getName())) {

            throw new Exception("cabin class already exists with given name");
        }
        SeatMap seatMap = SeatMapMapper.toEntity(request, cabinClass);
        seatMap.setAirlineId(airlineResponse.getId());
        SeatMap savedSeatMap = seatMapRepository.save(seatMap);

        seatService.generateSeats(savedSeatMap.getId());

        return SeatMapMapper.toResponse(savedSeatMap);

    }

    @Override
    public SeatMapResponse getSeatMapById(Long id) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id).orElseThrow(
                () -> new Exception("seat map not found with id"));

        return SeatMapMapper.toResponse(seatMap);
    }

    @Override
    public SeatMapResponse getSeatMapByCabinClass(Long cabinId) {
        SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinId);

        return SeatMapMapper.toResponse(seatMap);
    }

    @Override
    public SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id).orElseThrow(
                () -> new Exception("seat map not found with id"));

        SeatMapMapper.updateEntity(request, seatMap);
        SeatMap updated = seatMapRepository.save(seatMap);
        return SeatMapMapper.toResponse(updated);
    }

    @Override
    public void deleteSeatMap(Long id) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id).orElseThrow(
                () -> new Exception("seat map not found with id"));

        seatMapRepository.delete(seatMap);

    }

}
