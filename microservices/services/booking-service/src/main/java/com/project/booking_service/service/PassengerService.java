package com.project.booking_service.service;

import com.project.booking_service.model.Passenger;
import com.project.payload.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassenger(PassengerRequest request, Long userId);

}
