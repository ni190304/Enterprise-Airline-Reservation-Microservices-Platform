package com.project.payment_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.payload.dto.PaymentDTO;
import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.request.PaymentVerifyRequest;
import com.project.payload.response.PaymentInitiateResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;

    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException, Exception;

    Page<PaymentDTO> getAllPayments(Pageable pageable);

    Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds);

    

}
