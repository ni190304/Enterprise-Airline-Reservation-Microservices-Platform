package com.project.payload.request;

import java.time.ZoneId;

import com.project.embeddable.Address;
import com.project.embeddable.GeoCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {

    @NotBlank(message = "IATA Code is mandatory")
    @Size(max = 3,min = 3, message = "IATA code is mandatory")
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    private String name;

    private ZoneId timeZone;

    @Valid
    private Address address;

    @NotNull(message = "City ID is mandatory")
    private Long cityId;

    @Valid
    private GeoCode geoCode;




}
