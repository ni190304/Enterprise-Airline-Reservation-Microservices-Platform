package com.project.seat_service.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.seat_service.model.SeatInstance;
import com.project.seat_service.repository.SeatInstaneRepository;
import com.project.seat_service.services.SeatInstanceService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SeatInstanceImpl implements SeatInstanceService {

    private final SeatInstaneRepository seatInstaneRepository;

    @Override
    public Double calculateSeatPrice(List<Long> seatInstanceIds) {
        List<SeatInstance> seatInstances = seatInstaneRepository.findAllById(seatInstanceIds);

        double price = 0;

        for (SeatInstance seatInstance : seatInstances) {
            double seatPremium = seatInstance.getPremiumSupercharge()!=null? seatInstance.getPremiumSupercharge():0;
            price += seatPremium;
        }

        return price;
    }

}
