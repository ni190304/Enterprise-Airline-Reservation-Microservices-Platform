package com.project.payload.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalTime arrivalTime;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End_date is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;

}
