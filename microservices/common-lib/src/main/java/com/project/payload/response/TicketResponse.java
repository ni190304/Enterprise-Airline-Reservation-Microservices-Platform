package com.project.payload.response;

import java.time.LocalDateTime;

import com.project.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private Long id;

    private String ticketNumber;
    private TicketStatus status;
    private LocalDateTime issuedAt;

    // Booking details
    private Long bookingId;
    private String bookingReference;

    // Passenger details
    private Long passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    // Payment details
    private Long paymentId;
    private Double paymentAmount;
    private String paymentCurrency;
}
