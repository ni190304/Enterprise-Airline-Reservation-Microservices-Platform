package com.project.booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.booking_service.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            select t from Ticket t
            left join fetch t.booking
            left join fetch t.passenger
            where t.booking.id =: bookingId
            """)

    List<Ticket> findByBookingIdWithDetails(
            @Param("bookingId") Long bookingId);

    List<Ticket> findByBookIngId(long bookingId);

    boolean existsByTicketNumber(String ticketNumber);

}
