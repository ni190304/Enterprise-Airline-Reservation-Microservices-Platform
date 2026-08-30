package com.project.booking_service.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.booking_service.mapper.BookingMapper;
import com.project.booking_service.mapper.PaymentDTO;
import com.project.booking_service.model.Booking;
import com.project.booking_service.model.Passenger;
import com.project.booking_service.repository.BookingRepository;
import com.project.booking_service.service.BookingService;
import com.project.booking_service.service.PassengerService;
import com.project.booking_service.service.TicketService;
import com.project.enums.BookingStatus;
import com.project.payload.request.BookingRequest;
import com.project.payload.request.PassengerRequest;
import com.project.payload.response.BookingResponse;
import com.project.payload.response.FareResponse;
import com.project.payload.response.FlightCabinAncillaryResponse;
import com.project.payload.response.FlightInstanceResponse;
import com.project.payload.response.FlightMealResponse;
import com.project.payload.response.FlightResponse;
import com.project.payload.response.SeatInstanceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @Override
    public BookingResponse createBooking(BookingRequest request, Long userId) {

        // step 1: create uniq booking reference
        String bookingReference = generateBookingReference();

        // step 2: create passenger
        Set<Passenger> passengers = new HashSet<>();
        for (PassengerRequest passengerRequest : request.getPassengers()) {
            Passenger passenger = passengerService.createPassenger(passengerRequest, userId);
            passengers.add(passenger);
        }

        // todo : step 3 flight exist

        // step 4: create booking with pending status
        Booking booking = BookingMapper.toEntity(request,
                userId, passengers, bookingReference);

        // todo: set airline id from flightResponse
        booking.setAirlineId(1L);

        // step 5: set seat instance ids
        List<Long> seatInstanceIds = request.getPassengers().stream()
                .map(PassengerRequest::getSeatInstanceId)
                .collect(Collectors.toList());

        booking.setSeatInstanceIds(seatInstanceIds);

        booking = bookingRepository.save(booking);

        for (Passenger passenger1 : passengers) {
            passenger1.setBooking(booking);
        }

        ticketService.generateTicketsForBooking(booking);

        return convertToBookingResponse(booking);

    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }

    @Override
    public BookingResponse getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new Exception("booking not found with id"));

        return convertToBookingResponse(booking);

    }

    @Override
    public List<BookingResponse> getAllBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status,
            Long flightInstanceId, String sortDirection) {

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, "bookingDate");

        List<Booking> bookings = bookingRepository.findByAirlineWithFilter(airlineId, searchQuery, status,
                flightInstanceId, sort);

        return bookings.stream().map(
                this::convertToBookingResponse).toList();

    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {

        return bookingRepository.findByUserId(userId)
                .stream().map(this::convertToBookingResponse).toList();
    }

    @Override
    public BookingResponse cancelBooking(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new Exception("booking not found with id"));

        booking.setStatus(BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        return convertToBookingResponse(updated);

    }

    @Override
    public void deleteBooking(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new Exception("booking not found with id"));

        bookingRepository.delete(booking);
    }

    private String generateBookingReference() {
        String reference;

        do {
            reference = "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingReference(reference));

        return reference;
    }

    private BookingResponse convertToBookingResponse(Booking booking) {

        // todo : when enable feign cilent use actual responses

        List<FlightCabinAncillaryResponse> ancillaryResponses = new ArrayList<>();
        List<FlightMealResponse> mealResponses = new ArrayList<>();
        PaymentDTO paymentDTO = new PaymentDTO();
        FareResponse fareResponse = new FareResponse();
        FlightResponse flightResponse = new FlightResponse();

        List<SeatInstanceResponse> seatInstanceResponses = new ArrayList<>();
        FlightInstanceResponse flightInstanceResponse = new FlightInstanceResponse();

        return BookingMapper.toResponse(
                booking,
                paymentDTO,
                fareResponse,
                flightResponse,
                flightInstanceResponse,
                ancillaryResponses,
                mealResponses,
                seatInstanceResponses);

    }

}
