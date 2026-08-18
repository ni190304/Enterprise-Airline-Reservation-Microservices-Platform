package com.project.payload.response;

import java.util.ArrayList;
import java.util.List;

import com.project.enums.CabinClassType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FlightInstanceCabinResponse {

    private Long id;
    private Long flightInstanceId;
    private CabinClassType cabinClassType;
    private CabinClassResponse cabinClass;
    @Builder.Default
    private List<SeatInstanceResponse> seats = new ArrayList<>();
    @Builder.Default
    private SeatMapResponse seatMap = new SeatMapResponse();
    private Integer totalSeats;
    private Integer bookedSeats;
    private Integer availableSeats;
    private Boolean isActive;
    private Boolean canBook;

}
