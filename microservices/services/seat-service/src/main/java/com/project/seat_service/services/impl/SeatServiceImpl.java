package com.project.seat_service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.enums.SeatType;
import com.project.payload.request.SeatRequest;
import com.project.payload.response.SeatResponse;
import com.project.seat_service.mapper.SeatMapper;
import com.project.seat_service.model.Seat;
import com.project.seat_service.model.SeatMap;
import com.project.seat_service.repository.SeatMapRepository;
import com.project.seat_service.repository.SeatRepository;
import com.project.seat_service.services.SeatService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapRepository seatMapRepository;

    @Override
    public void generateSeats(Long seatMapId) throws Exception {
        boolean exists = seatRepository.existsBySeatMapId(seatMapId);

        if (exists) {
            throw new Exception("seats already created for seat map");
        }

        SeatMap seatMap = seatMapRepository.findById(seatMapId).orElseThrow(
                () -> new Exception("seat map not found"));

        int leftSeatsPerRow = seatMap.getLeftSeatsPerRow();
        int rightSeatsPerRow = seatMap.getRightSeatsPerRow();
        int rows = seatMap.getTotalRows();
        int seatsPerRow = leftSeatsPerRow + rightSeatsPerRow;

        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < seatsPerRow; col++) {
                String seatNum = row + getSeatLetter(col);
                SeatType type = getSeatType(col, leftSeatsPerRow, rightSeatsPerRow);
                Seat seat = Seat.builder()
                        .seatNumber(seatNum)
                        .seatRow(row)
                        .columnLetter(getSeatLetter(col).charAt(0))
                        .seatType(type)
                        .seatMap(seatMap)
                        .build();

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }

    private SeatType getSeatType(int col, int leftSeatsPerRow, int rightSeatsPerRow) {

        int totalSeats = leftSeatsPerRow + rightSeatsPerRow;

        // windows
        if (col == 0 || col == totalSeats - 1)
            return SeatType.WINDOW;
        // left aisle
        if (col == leftSeatsPerRow - 1)
            return SeatType.AISLE;
        // right aisle
        if (col == leftSeatsPerRow)
            return SeatType.AISLE;
        return SeatType.MIDDLE;

    }

    private String getSeatLetter(int col) {
        StringBuilder sb = new StringBuilder();
        while (col >= 0) {
            sb.insert(0, (char) ('A' + (col % 26)));
            col = col / 26 - 1;
        }

        return sb.toString();
    }

    @Override
    public List<SeatResponse> getAll() {
        return seatRepository.findAll().stream().map(SeatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse updateSeats(Long seatId, SeatRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSeats'");
    }

}
