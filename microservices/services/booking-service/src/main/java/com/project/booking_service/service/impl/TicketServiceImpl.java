
package com.project.booking_service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.booking_service.model.Booking;
import com.project.booking_service.model.Passenger;
import com.project.booking_service.model.Ticket;
import com.project.booking_service.repository.TicketRepository;
import com.project.booking_service.service.TicketService;
import com.project.enums.TicketStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {

        List<Ticket> tickets = new ArrayList<>();

        if (booking == null || booking.getId() == null) {
            throw new IllegalArgumentException("Booking must be saved first");
        }

        if (booking.getPassengers() == null) {
            return tickets;
        }

        for (Passenger passenger : booking.getPassengers()) {

            if (passenger.getId() == null) {
                throw new IllegalArgumentException("Passenger must be saved first");
            }

            if (ticketRepository.existsByBookingIdAndPassengerId(
                    booking.getId(), passenger.getId())) {
                continue;
            }

            Ticket ticket = Ticket.builder()
                    .ticketNumber(generateUniqueTicketNumber())
                    .status(TicketStatus.BOOKED)
                    .issuedAt(LocalDateTime.now())
                    .booking(booking)
                    .passenger(passenger)
                    .build();

            tickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        // Keep the Booking entity's ticket collection in sync.
        booking.getTickets().addAll(savedTickets);

        return savedTickets;
    }

    private String generateUniqueTicketNumber() {
        String ticketNumber;

        do {
            String datePart = LocalDateTime.now().toString().substring(0, 10);
            String randomPart = UUID.randomUUID().toString().substring(0, 8);
            ticketNumber = String.format("TKT-%s-%s", datePart, randomPart);
        } while (ticketRepository.existsByTicketNumber(ticketNumber));

        return ticketNumber;
    }

    @Override
    public void cancelTicketsForBooking(Long bookingId) {

        List<Ticket> tickets = ticketRepository.findByBookingId(bookingId);

        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.CANCELLED);
        }

        ticketRepository.saveAll(tickets);
    }
}
