package com.project.payload.request;

import com.project.enums.AirlineStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {

    @NotBlank(message = "iata code is mandatory")
    @Size(min = 2,max = 2,message = "IATA code must be exactly 2 char.")
    private String iataCode;

    @NotBlank(message = "icao code is mandatory")
    @Size(min = 3,max = 3,message = "ICAO code must be exactly 3 char.")
    private String icaoCode;

    @NotBlank(message = "airline name is mandatory")
    private String name;


    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Long headqueartersCityId;

    private String supportEmail;
    private String supportPhone;
    private String supportHours;

}
