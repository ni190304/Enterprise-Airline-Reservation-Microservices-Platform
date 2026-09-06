package com.project.booking_service.service.integration;

import org.springframework.stereotype.Service;

import com.project.booking_service.client.PricingClient;
import com.project.payload.response.FareResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FareIntegrationService {

    private final PricingClient pricingClient;

    public Double calculateFareTotal(Long fareId) {
        FareResponse fareResponse = pricingClient.getFareById(fareId);
        Double baseFare = fareResponse.getBaseFare();
        Double taxesAndFees = fareResponse.getTaxesAndFees() != null ? fareResponse.getTaxesAndFees() : 0;
        Double airlineFees = fareResponse.getAirlineFees() != null ? fareResponse.getAirlineFees() : 0;

        return baseFare + taxesAndFees + airlineFees;
    }

}
