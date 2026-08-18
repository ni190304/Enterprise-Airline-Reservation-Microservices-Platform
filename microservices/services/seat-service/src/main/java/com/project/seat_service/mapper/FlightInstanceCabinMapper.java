package com.project.seat_service.mapper;

import com.project.payload.response.FlightInstanceCabinResponse;
import com.project.seat_service.model.FlightInstanceCabin;

public class FlightInstanceCabinMapper {

    public static FlightInstanceCabinResponse toResponse(FlightInstanceCabin fic) {
        if (fic == null)
            return null;

        return FlightInstanceCabinResponse.builder()
                .id(fic.getId())
                .flightInstanceId(fic.getFlightInstanceId())
                .cabinClassType(fic.getCabinClass().getName())
                .cabinClass(CabinClassMapper.toResponse(
                        fic.getCabinClass()))

                // todo : set seat instance
                // .seats ()

                .seatMap(fic.getCabinClass() != null && fic.getCabinClass().getSeatMap() != null
                        ? SeatMapMapper.toSimpleResponse(fic.getCabinClass().getSeatMap())
                        : null)
                .totalSeats(fic.getTotalSeats())
                .bookedSeats(fic.getBookedSeats())
                .availableSeats(fic.getAvailableSeats())
                .build();
    }
}
