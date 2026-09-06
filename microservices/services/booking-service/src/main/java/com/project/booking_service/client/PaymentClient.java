package com.project.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.response.PaymentInitiateResponse;

import jakarta.validation.Valid;

@FeignClient("payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments/initiate")
    PaymentInitiateResponse initiatePayment(
            @Valid @RequestBody PaymentInitiateRequest paymentInitiateRequest);

}
