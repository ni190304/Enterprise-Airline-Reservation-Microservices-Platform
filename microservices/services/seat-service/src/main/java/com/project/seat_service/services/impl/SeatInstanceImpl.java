package com.project.seat_service.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.enums.SeatAvailabilityStatus;
import com.project.payload.response.SeatInstanceResponse;
import com.project.seat_service.mapper.SeatInstanceMapper;
import com.project.seat_service.model.SeatInstance;
import com.project.seat_service.repository.SeatInstanceRepository;
import com.project.seat_service.services.SeatInstanceService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SeatInstanceImpl implements SeatInstanceService {

    private final SeatInstanceRepository seatInstanceRepository;

    @Override
    public Double calculateSeatPrice(List<Long> seatInstanceIds) {
        List<SeatInstance> seatInstances = seatInstanceRepository.findAllById(seatInstanceIds);

        double price = 0;

        for (SeatInstance seatInstance : seatInstances) {
            double seatPremium = seatInstance.getPremiumSupercharge()!=null? seatInstance.getPremiumSupercharge():0;
            price += seatPremium;
        }

        return price;
    }

	@Override
	public SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId, SeatAvailabilityStatus status) {
		SeatInstance seatInstance = seatInstanceRepository.findById(seatInstanceId).orElse(null);

        if (seatInstance==null) {
            return null;
        }
        seatInstance.setStatus(status);
        seatInstanceRepository.save(seatInstance);

        return SeatInstanceMapper.toResponse(seatInstance);
	}

}
