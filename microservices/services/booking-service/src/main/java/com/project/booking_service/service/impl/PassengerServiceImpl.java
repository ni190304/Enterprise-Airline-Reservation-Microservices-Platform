package com.project.booking_service.service.impl;

import org.springframework.stereotype.Service;

import com.project.booking_service.mapper.PassengerMapper;
import com.project.booking_service.model.Passenger;
import com.project.booking_service.repository.PassengerRepository;
import com.project.booking_service.service.PassengerService;
import com.project.payload.request.PassengerRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public Passenger createPassenger(PassengerRequest request, Long userId) {

        Passenger passenger = PassengerMapper.toEntity(request);
        passenger.setPrimaryUserId(userId);
        Passenger saved = passengerRepository.save(passenger);
        return saved;

    }

}
