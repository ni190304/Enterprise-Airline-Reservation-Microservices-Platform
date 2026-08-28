package com.project.booking_service.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.booking_service.model.Passenger;
import com.project.booking_service.repository.BookingRepository;
import com.project.booking_service.service.BookingService;
import com.project.booking_service.service.PassengerService;
import com.project.enums.BookingStatus;
import com.project.payload.request.BookingRequest;
import com.project.payload.request.PassengerRequest;
import com.project.payload.response.BookingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;

    @Override
    public BookingResponse createBooking(BookingRequest request, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBooking'");
    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingById'");
    }

    @Override
    public List<BookingResponse> getAllBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status,
            Long flightInstanceId, String sortDirection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllBookingsByAirline'");
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsByUser'");
    }

    @Override
    public BookingResponse cancelBooking(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelBooking'");
    }

    @Override
    public void deleteBooking(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBooking'");
    }

    private String generateBookingReference() {
        String reference;

        do {
            reference = "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingReference(reference));

        return reference;
    }

}
