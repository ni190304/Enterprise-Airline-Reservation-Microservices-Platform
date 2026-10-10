package com.project.flight_ops_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.enums.CabinClassType;
import com.project.payload.response.CabinClassResponse;

@FeignClient(name = "seat-service")
public interface SeatClient {

    @PostMapping("/api/seat-instances/price/price")
    Double calculateSeatPrice(@RequestBody List<Long> seatInstanceIds);

    @GetMapping("/api/cabin-classes/aircraft/{id}/name/{cabinClass}")
    CabinClassResponse getCabinClassByAircraftIdAndName(
            @PathVariable CabinClassType cabinClass,
            @PathVariable Long id);

}
