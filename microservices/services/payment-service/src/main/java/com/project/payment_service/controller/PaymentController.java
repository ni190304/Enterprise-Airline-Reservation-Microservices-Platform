package com.project.payment_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.dto.PaymentDTO;
import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.request.PaymentVerifyRequest;
import com.project.payload.response.PaymentInitiateResponse;
import com.project.payment_service.service.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
            @Valid @RequestBody PaymentInitiateRequest paymentInitiateRequest) throws RazorpayException {
        PaymentInitiateResponse response = paymentService.initiatePayment(
                paymentInitiateRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request)
            throws Exception {
        PaymentDTO payment = paymentService.verifyPayment(request);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/batch/bookings")
    public ResponseEntity<Map<Long, PaymentDTO>> getPaymentsByBookingIds(@RequestBody List<Long> bookingIds) {
        return ResponseEntity.ok(paymentService
                .getPaymentsByBookingIds(bookingIds));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getALLPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestHeader("X-User-Id") Long userId) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }

}
