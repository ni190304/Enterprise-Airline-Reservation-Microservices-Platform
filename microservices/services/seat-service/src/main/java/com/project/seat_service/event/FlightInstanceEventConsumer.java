package com.project.seat_service.event;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.enums.SeatAvailabilityStatus;
import com.project.enums.SeatType;
import com.project.event.FlightInstanceCreatedEvent;
import com.project.seat_service.model.CabinClass;
import com.project.seat_service.model.FlightInstanceCabin;
import com.project.seat_service.model.Seat;
import com.project.seat_service.model.SeatInstance;
import com.project.seat_service.repository.CabinClassRepository;
import com.project.seat_service.repository.FlightInstanceCabinRepository;
import com.project.seat_service.repository.SeatInstanceRepository;
import com.project.seat_service.repository.SeatRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventConsumer {

    private final CabinClassRepository cabinClassRepository;
    private final SeatRepository seatRepository;
    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @PostConstruct
    public void testConsumerStarted() {
        System.out.println("🔥 SEAT SERVICE KAFKA CONSUMER STARTED");
    }

    @KafkaListener(
    topics = "flight-instance-created",
    groupId = "seat-service-debug-123",
    containerFactory = "kafkaListenerContainerFactory"
)
    @Transactional
    public void handleFlightInstanceCreated(FlightInstanceCreatedEvent event) {

        System.out.println("🔥🔥🔥 EVENT RECEIVED 🔥🔥🔥");

        System.out.println(
                "Flight ID: " + event.getFlightId()
                        + ", Instance ID: " + event.getFlightInstanceId()
                        + ", Aircraft ID: " + event.getAircraftId());

        List<CabinClass> cabinClasses = cabinClassRepository.findByAircraftId(event.getAircraftId());

        int totalSeatInstances = 0;

        for (CabinClass cabinClass : cabinClasses) {
            List<Seat> seats = cabinClass.getSeatMap() != null
                    ? seatRepository.findBySeatMapId(cabinClass.getSeatMap().getId())
                    : List.of();

            FlightInstanceCabin fic = FlightInstanceCabin.builder()
                    .flightInstanceId(event.getFlightInstanceId())
                    .cabinClass(cabinClass)
                    .totalSeats(seats.size())
                    .bookedSeats(0)
                    .build();

            FlightInstanceCabin savedFic = flightInstanceCabinRepository.save(fic);

            List<SeatInstance> seatInstances = seats.stream()
                    .map(seat -> SeatInstance.builder()
                            .flightId(event.getFlightId())
                            .flightInstanceId(event.getFlightInstanceId())
                            .flightInstanceCabin(savedFic)
                            .seat(seat)
                            .status(SeatAvailabilityStatus.AVAILABLE)
                            .isBooked(false)
                            .isAvailable(true)
                            .premiumSupercharge(
                                    getPremiumSuperCharge(
                                            seat.getSeatType(),
                                            1000.0, 500.0))
                            .build())
                    .toList();

            seatInstanceRepository.saveAll(seatInstances);
            totalSeatInstances += seatInstances.size();

        }
    }

    private Double getPremiumSuperCharge(SeatType seatType,
            Double windowSuperCharge,
            Double aisleSuperCharge) {

        if (seatType == null)
            return 0.0;

        return switch (seatType) {
            case AISLE -> aisleSuperCharge;
            case WINDOW -> windowSuperCharge;
            default -> 0.0;
        };
    }
}