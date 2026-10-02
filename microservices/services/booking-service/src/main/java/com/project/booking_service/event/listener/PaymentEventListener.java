package com.project.booking_service.event.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.booking_service.client.FlightClient;
import com.project.booking_service.client.PricingClient;
import com.project.booking_service.model.Booking;
import com.project.booking_service.repository.BookingRepository;
import com.project.enums.BookingStatus;
import com.project.event.PaymentCompletedEvent;
import com.project.event.PaymentFailedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final PricingClient pricingClient;

    @KafkaListener(topics = "payment.completed", groupId = "booking-service-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event) throws Exception {

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if (booking == null) {
            return;
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        // FlightInstanceResponse flightInstanceResponse=flightClient
        // .getFlightInstanceById(booking.getFlightInstanceId());
        // FareResponse fareResponse=pricingClient.getFareById(booking.getFareId());
        // UserDTO userDTO=userClient.getUserById(booking.getUserId());

        // publish event for seat-service and notification service
    }

    @KafkaListener(topics = "payment.failed", groupId = "booking-service-group")
    public void handlePaymentFailed(PaymentFailedEvent event) throws Exception {
        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if (booking == null) {
            return;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
