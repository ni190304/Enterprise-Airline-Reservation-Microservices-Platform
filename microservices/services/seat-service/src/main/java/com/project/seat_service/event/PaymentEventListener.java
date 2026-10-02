package com.project.seat_service.event;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.enums.SeatAvailabilityStatus;
import com.project.event.PaymentCompletedEvent;
import com.project.payload.response.BookingResponse;
import com.project.payload.response.SeatInstanceResponse;
import com.project.seat_service.client.BookingClient;
import com.project.seat_service.services.SeatInstanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingClient bookingClient;
    private final SeatInstanceService seatInstanceService;

    @KafkaListener(topics = "payment.completed", groupId = "seat-service-group")
    public void handleBookingConfirmed(PaymentCompletedEvent event) {

        BookingResponse bookingResponse = bookingClient.getBookingById(event.getBookingId());

        List<SeatInstanceResponse> seatInstances = bookingResponse.getSeatInstances();

        for (SeatInstanceResponse seatInstanceResponse : seatInstances) {
            seatInstanceService.updateSeatInstanceStatus(
                    seatInstanceResponse.getId(),
                    SeatAvailabilityStatus.BOOKED);

        }
    }
}
