package com.project.seat_service.services;

import java.util.List;

import com.project.payload.request.SeatRequest;
import com.project.payload.response.SeatResponse;

public interface SeatService {

    void generateSeats(Long seatMapId) throws Exception;

    List<SeatResponse> getAll();

    SeatResponse updateSeats(Long seatId, SeatRequest request);

}
