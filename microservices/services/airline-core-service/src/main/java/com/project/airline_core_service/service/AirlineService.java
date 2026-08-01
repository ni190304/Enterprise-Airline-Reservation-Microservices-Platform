package com.project.airline_core_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.enums.AirlineStatus;
import com.project.payload.request.AirlineRequest;
import com.project.payload.response.AirlineDropdownItem;
import com.project.payload.response.AirlineResponse;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request, Long ownerId);

    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;

    AirlineResponse getAirlineById(Long id) throws Exception;

    Page<AirlineResponse> getAllAirlines(Pageable aPageable);

    AirlineResponse updateAirline(AirlineRequest request , Long ownerId) throws Exception;

    void deleteAirline(Long id, Long ownerId) throws Exception;

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception;

    List<AirlineDropdownItem> getAirlineDropdown();

}
