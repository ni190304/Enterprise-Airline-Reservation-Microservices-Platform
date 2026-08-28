package com.project.booking_service.service;

import java.util.List;

import com.project.booking_service.model.Booking;
import com.project.booking_service.model.Ticket;

public interface TicketService {

    List<Ticket> generateTicketsForBooking(Booking booking);
}
