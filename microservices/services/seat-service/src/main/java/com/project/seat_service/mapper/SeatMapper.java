package com.project.seat_service.mapper;

import com.project.payload.response.SeatResponse;
import com.project.seat_service.model.Seat;

public class SeatMapper {

    public static SeatResponse toResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .columnLetter(seat.getColumnLetter())
                .seatType(seat.getSeatType())
                .isAvailable(seat.getIsAvailable())
                .isBlocked(seat.getIsBlocked())
                .isActive(seat.getIsActive())
                .basePrice(seat.getBasePrice())
                .premiumSurcharge(seat.getPremiumSuperCharge())
                .totalPrice(seat.getTotalPrice())
                .hasExtraLegroom(seat.getHasExtraLegroom())
                .hasPowerOutlet(seat.getHasPowerOutlet())
                .hasTvScreen(seat.getHasTvScreen())
                .hasExtraWidth(seat.getHasExtraWidth())
                .seatPitch(seat.getSeatPitch())
                .seatWidth(seat.getSeatWidth())
                .seatMapId(seat.getSeatMap() != null ? seat.getSeatMap().getId() : null)
                .seatMapName(seat.getSeatMap() != null ? seat.getSeatMap().getName() : null)
                .cabinClassId(seat.getCabinClass() != null ? seat.getCabinClass().getId() : null)
                .cabinClassName(seat.getCabinClass() != null ? seat.getCabinClass().getName().toString() : null)
                .createdAt(seat.getCreatedAt())
                .updatedAt(seat.getUpdatedAt())
                .createdBy(seat.getCreatedBy())
                .updatedBy(seat.getUpdatedBy())
                // .isBookable(false)
                .fullPosition(seat.getFullPosition())
                .build();

    }

    

}
