package com.project.booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.booking_service.service.BookingService;
import com.project.enums.BookingStatus;
import com.project.payload.request.BookingRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.BookingResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(

                        bookingService.createBooking(bookingRequest, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) throws Exception {
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/airline")
    public ResponseEntity<List<BookingResponse>> getBookingsByAirline(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) Long flightInstanceId,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestHeader("X-Airline-Id") Long airlineId) {

        List<BookingResponse> responses = bookingService.getAllBookingsByAirline(
                airlineId,
                search,
                status,
                flightInstanceId,
                sortDirection);

        return ResponseEntity.ok(responses);

    }

    @GetMapping("/user/history")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(
            @RequestHeader("X-User-Id") Long userId) {
        List<BookingResponse> responses = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        BookingResponse response = bookingService.cancelBooking(id);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse> deleteBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        bookingService.deleteBooking(id);
        ApiResponse response = new ApiResponse("Booking deleted successfully");
        return ResponseEntity.ok(response);
    }

}
