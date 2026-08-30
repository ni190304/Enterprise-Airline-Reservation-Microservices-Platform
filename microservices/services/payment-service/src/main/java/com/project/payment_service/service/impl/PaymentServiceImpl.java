package com.project.payment_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.enums.PaymentGateway;
import com.project.payload.dto.PaymentDTO;
import com.project.payload.dto.UserDTO;
import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.request.PaymentVerifyRequest;
import com.project.payload.response.PaymentInitiateResponse;
import com.project.payload.response.PaymentLinkResponse;
import com.project.payload.response.PaymentStatus;
import com.project.payment_service.mapper.PaymentMapper;
import com.project.payment_service.model.Payment;
import com.project.payment_service.repository.PaymentRepository;
import com.project.payment_service.service.PaymentService;
import com.project.payment_service.service.gateway.RazorPayService;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorPayService razorPayService;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {

        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if (payment.getStatus() == PaymentStatus.SUCCESS) {
                        throw new RuntimeException("payment already completed for this booking");
                    }
                });

        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getGateway())
                .status(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();

        payment = paymentRepository.save(payment);

        PaymentInitiateResponse response = PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .transactionId(payment.getTransactionId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .success(true)
                .message("payment initiated successfully")
                .build();

        if (request.getGateway() == PaymentGateway.RAZORPAY) {

            UserDTO userDTO = new UserDTO();
            userDTO.setId(1L);
            userDTO.setFullName("Roshan Kumar");
            userDTO.setEmail("roshan.kumar@gmail.com");
            userDTO.setPhone("8989899898");

            PaymentLinkResponse paymentLinkResponse = razorPayService.createPaymentLink(userDTO, payment);

            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_url());

        }

        return response;

    }

    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 0).toUpperCase();
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception {

        JSONObject paymentDetails = razorPayService.fetchPaymentDetails(request.getRazorpayPaymentId());

        String status = paymentDetails.optString("status");

        JSONObject notes = paymentDetails.getJSONObject("notes");
        Long paymentId = Long.parseLong(notes.optString("payment_id"));

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new Exception("payment not found"));

        boolean isValid = "captured".equalsIgnoreCase(status);
        if (isValid) {
            if (payment.getProvider() == PaymentGateway.RAZORPAY) {
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            // todo : publish kafka event
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);

            // todo : publish kafka event
        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {

        return paymentRepository.findAll(pageable).map(
                PaymentMapper::toDTO);
    }

    @Override
    public Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream().collect(Collectors.toMap(Payment::getId, PaymentMapper::toDTO));
    }

}
