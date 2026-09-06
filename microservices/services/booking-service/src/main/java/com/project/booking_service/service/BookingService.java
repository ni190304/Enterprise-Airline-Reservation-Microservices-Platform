package com.project.booking_service.service;

import java.util.List;

import com.project.enums.BookingStatus;
import com.project.payload.request.BookingRequest;
import com.project.payload.response.BookingResponse;
import com.project.payload.response.PaymentInitiateResponse;

public interface BookingService {

    PaymentInitiateResponse createBooking(BookingRequest request, Long userId);

    BookingResponse updateBooking(Long id, BookingRequest request);

    BookingResponse getBookingById(Long id) throws Exception;

    List<BookingResponse> getAllBookingsByAirline(Long airlineId,
            String searchQuery,
            BookingStatus status,
            Long flightInstanceId,
            String sortDirection

    );

    List<BookingResponse> getBookingsByUser(Long userId);

    BookingResponse cancelBooking(Long id) throws Exception;

    void deleteBooking(Long id) throws Exception;

}
