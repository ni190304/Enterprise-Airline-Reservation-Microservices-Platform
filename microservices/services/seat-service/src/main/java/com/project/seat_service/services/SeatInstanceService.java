package com.project.seat_service.services;

import java.util.List;

import com.project.enums.SeatAvailabilityStatus;
import com.project.payload.response.SeatInstanceResponse;

public interface SeatInstanceService {

    Double calculateSeatPrice(List<Long> seatInstanceIds);

    SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId, SeatAvailabilityStatus status);

}
