package com.project.booking_service.event.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.booking_service.client.FlightClient;
import com.project.booking_service.client.PricingClient;
import com.project.booking_service.client.UserClient;
import com.project.booking_service.model.Booking;
import com.project.booking_service.repository.BookingRepository;
import com.project.enums.BookingStatus;
import com.project.event.PaymentCompletedEvent;
import com.project.event.PaymentFailedEvent;
import com.project.payload.dto.UserDTO;
import com.project.payload.response.FareResponse;
import com.project.payload.response.FlightInstanceResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor 
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final PricingClient pricingClient;
    private final UserClient userClient;
    // private final BookingEventProducer bookingEventProducer;

    @KafkaListener(topics = "payment-completed", groupId = "booking-service-group")
    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) throws Exception {

        System.out.println("Received PaymentCompletedEvent "
                + event.getBookingId()+"-"
                + event.getPaymentId());

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if(booking == null) {return;}

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        FlightInstanceResponse flightInstanceResponse=flightClient
                .getFlightInstanceById(booking.getFlightInstanceId());
        FareResponse fareResponse=pricingClient.getFareById(booking.getFareId());
        UserDTO userDTO=userClient.getUserById(booking.getUserId());


        // bookingEventProducer.sendBookingConfirmed(booking,event,flightInstanceResponse,
        //         fareResponse,userDTO);

    }

    @KafkaListener(topics = "payment-failed",groupId = "booking-service-group")
    public void handlePaymentFailed(PaymentFailedEvent event) throws Exception {
        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if(booking == null) {return;}

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
