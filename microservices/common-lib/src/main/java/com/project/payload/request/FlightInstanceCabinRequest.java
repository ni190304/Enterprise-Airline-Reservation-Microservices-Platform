package com.project.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstanceCabinRequest {

    @NotNull
    private Long flightId;

    @NotNull(message = "Flight instance ID is required")
    private Long flightInstanceId;

    @NotNull
    private Long cabinClassId;

    @NotNull
    @Positive
    private Double baseFare;

    @NotNull
    private Double windowSurcharge;

    @NotNull
    private Double aiseSurcharge;
}
