package com.project.flight_ops_service.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.enums.CabinClassType;
import com.project.flight_ops_service.client.AirlineClient;
import com.project.flight_ops_service.client.LocationClient;
import com.project.flight_ops_service.client.PricingClient;
import com.project.flight_ops_service.client.SeatClient;
import com.project.flight_ops_service.mapper.FlightInstanceMapper;
import com.project.flight_ops_service.model.FlightInstance;
import com.project.flight_ops_service.repository.FlightInstanceRepository;
import com.project.flight_ops_service.service.FlightSearchService;
import com.project.flight_ops_service.service.specification.FlightInstanceSpecification;
import com.project.payload.request.FlightSearchRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.CabinClassResponse;
import com.project.payload.response.FareResponse;
import com.project.payload.response.FlightInstanceResponse;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final PricingClient pricingClient;
    private final SeatClient seatClient;
    private final AirlineClient airlineClient;
    private final LocationClient locationClient;

    public FlightSearchServiceImpl(
            FlightInstanceRepository flightInstanceRepository,
            PricingClient pricingClient,
            SeatClient seatClient,
            AirlineClient airlineClient,
        LocationClient locationClient) {

        this.flightInstanceRepository = flightInstanceRepository;
        this.pricingClient = pricingClient;
        this.seatClient = seatClient;
        this.airlineClient = airlineClient;
        this.locationClient = locationClient;
    }

    @Override
    public Page<FlightInstanceResponse> searchFlights(
            FlightSearchRequest request,
            Pageable pageable) {

        Pageable sortedPageable = applySort(
                pageable,
                request.getSortBy(),
                request.getSortOrder());

        Specification<FlightInstance> specification = FlightInstanceSpecification.buildSearchSpec(request);

        Page<FlightInstance> dbPage = flightInstanceRepository.findAll(specification, sortedPageable);

        if (dbPage.isEmpty()) {
            return Page.empty(sortedPageable);
        }

        List<FlightInstance> instances = new ArrayList<>(dbPage.getContent());

        Map<Long, FareResponse> fareMap = Collections.emptyMap();

        if (request.getCabinClass() != null) {

            final boolean hasPriceFilter = request.getMinPrice() != null
                    && request.getMaxPrice() != null;

            Map<Long, FareResponse> mergedFareMap = new HashMap<>();

            List<FlightInstance> filtered = new ArrayList<>();

            for (FlightInstance fi : instances) {

                CabinClassResponse cabinClassResponse = seatClient.getCabinClassByAircraftIdAndName(
                        request.getCabinClass(),
                        fi.getFlight().getAircraftId());

                if (cabinClassResponse == null) {
                    continue;
                }

                Long cabinClassId = cabinClassResponse.getId();

                if (cabinClassId == null) {
                    continue;
                }

                FareResponse fare = pricingClient.getLowestFareForFlightAndCabinClass(
                        fi.getFlight().getId(),
                        cabinClassId);

                if (fare == null) {
                    continue;
                }

                if (hasPriceFilter) {

                    Double price = fare.getTotalPrice();

                    if (price == null) {
                        continue;
                    }

                    if (price < request.getMinPrice()) {
                        continue;
                    }

                    if (price > request.getMaxPrice()) {
                        continue;
                    }
                }

                mergedFareMap.put(
                        fi.getFlight().getId(),
                        fare);

                filtered.add(fi);
            }

            instances = filtered;
            fareMap = mergedFareMap;

            if (instances.isEmpty()) {
                return Page.empty(sortedPageable);
            }
        }

        List<FlightInstanceResponse> responses = enrichWithExternalData(instances, fareMap);

        return new PageImpl<>(responses, sortedPageable, dbPage.getTotalElements());
    }

    private List<FlightInstanceResponse> enrichWithExternalData(List<FlightInstance> instances,
            Map<Long, FareResponse> fareMap) {

        Map<Long, AirlineResponse> airlineCache = new HashMap<>();
        Map<Long, AirportResponse> airportCache = new HashMap<>();
        Map<Long, AircraftResponse> aircraftCache = new HashMap<>();

        List<FlightInstanceResponse> results = new ArrayList<>(instances.size());

        for (FlightInstance fi : instances) {
            AircraftResponse aircraft = aircraftCache.computeIfAbsent(
                    fi.getFlight().getAircraftId(), airlineClient::getAircraftById);

            AirlineResponse airline = airlineCache.computeIfAbsent(
                    fi.getAirlineId(), airlineClient::getAirlineById);

            AirportResponse depAirport = airportCache.computeIfAbsent(
                    fi.getDepartureAirportId(), locationClient::getAirportById);

            AirportResponse arrAirport = airportCache.computeIfAbsent(
                    fi.getArrivalAirportId(), locationClient::getAirportById);

            FlightInstanceResponse response = FlightInstanceMapper.toResponse(
                    fi, aircraft, airline, depAirport, arrAirport);
            response.setFare(fareMap.get(fi.getFlight().getId()));
            results.add(response);
        }
        return results;
    }

    private Long resolveCabinClassId(
            CabinClassType cabinClass,
            Long aircraftId) {

        return null;
    }

    private Pageable applySort(
            Pageable pageable,
            String sortBy,
            String sortOrder) {

        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = (sortBy == null || sortBy.isBlank())
                ? Sort.by(direction, "departureDateTime")
                : switch (sortBy.toLowerCase()) {

                    case "arrival" ->
                        Sort.by(direction, "arrivalDateTime");

                    case "duration" ->
                        JpaSort.unsafe(
                                direction,
                                "TIMESTAMPDIFF(MINUTE, departureDateTime, arrivalDateTime)");

                    default ->
                        Sort.by(
                                direction,
                                "departureDateTime");
                };

        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort);
    }
}