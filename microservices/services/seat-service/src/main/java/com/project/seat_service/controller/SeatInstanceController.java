package com.project.seat_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.seat_service.services.SeatInstanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seat-instances")
@RequiredArgsConstructor
public class SeatInstanceController {

    private final SeatInstanceService seatInstanceService;

    @PostMapping("/price/price")
    public Double calculateSeatPrice(@RequestBody List<Long> seatInstanceIds) {
        return seatInstanceService.calculateSeatPrice(seatInstanceIds);
    }

}
