package com.project.notification_service.service;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.project.event.BookingConfirmedEvent;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${notification.from-email}")
    private String fromEmail;

    @Value("${notification.from-name}")
    private String fromName;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);

    public void sendBookingConfirmation(BookingConfirmedEvent booking)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(fromEmail, fromName);
        mimeMessageHelper.setTo(booking.getContactEmail());
        mimeMessageHelper.setSubject(buildSubject(booking));
        mimeMessageHelper.setText(buildHtmlBody(booking));
    }

    private String buildHtmlBody(BookingConfirmedEvent booking) {
        Context ctx = new Context();

        ctx.setVariable("booking", booking);
        ctx.setVariable("passengerVariable", booking.getPassengers() != null ? booking.getPassengers().size() : 1);

        // Formatted dates / times
        ctx.setVariable("depDate",
                booking.getDepartureDateTime() != null ? booking.getDepartureDateTime().format(DATE_FMT) : "N/A");
        ctx.setVariable("depTime",
                booking.getDepartureDateTime() != null ? booking.getDepartureDateTime().format(TIME_FMT) : "N/A");
        ctx.setVariable("arrDate",
                booking.getArrivalDateTime() != null ? booking.getArrivalDateTime().format(DATE_FMT) : "N/A");
        ctx.setVariable("arrTime",
                booking.getArrivalDateTime() != null ? booking.getArrivalDateTime().format(TIME_FMT) : "N/A");
        ctx.setVariable("paidAt",
                booking.getPaidAt() != null ? booking.getPaidAt().format(DT_FMT) : "N/A");
        ctx.setVariable("bookingDate",
                booking.getBookingDate() != null ? booking.getBookingDate().format(DT_FMT) : "N/A");

        double base = orZero(booking.getBaseFare());
        double taxes = orZero(booking.getTaxesAndFees());
        double seats = orZero(booking.getSeatFees());
        double ancillary = orZero(booking.getAncillaryFees());
        double meals = orZero(booking.getMealFees());
        double total = orZero(booking.getTotalAmount());

        ctx.setVariable("baseFareTotal", fmt(base));
        ctx.setVariable("taxes", fmt(taxes));
        ctx.setVariable("seatFees", fmt(seats));
        ctx.setVariable("ancillaryFees", fmt(ancillary));
        ctx.setVariable("mealFees", fmt(meals));
        ctx.setVariable("totalAmount", fmt(total));

        // Baggage helpers
        ctx.setVariable("hasBaggage",
                booking.getCheckinBaggagePieces() != null || booking.getCabinBaggagePieces() != null);
        ctx.setVariable("checkinBaggage", baggageLabel(
                booking.getCheckinBaggagePieces(), booking.getCheckinBaggageWeightPerPiece()));
        ctx.setVariable("cabinBaggage", baggageLabel(
                booking.getCabinBaggagePieces(), booking.getCabinBaggageWeightPerPiece()));

        ctx.setVariable("cabinClassDisplay", cabinDisplayName(booking.getCabinClass()));

        return templateEngine.process("email/booking-confirmation", ctx);

    }

    private static String cabinDisplayName(String cabinClass) {
        if (cabinClass == null)
            return "Economy";
        return switch (cabinClass) {
            case "ECONOMY" -> "Economy";
            case "PREMIUM_ECONOMY" -> "Premium Economy";
            case "BUSINESS" -> "Business";
            case "FIRST" -> "First Class";
            default -> cabinClass;
        };

    }

    private Object baggageLabel(Integer pieces, Double weightPerPiece) {

        if (pieces == null && weightPerPiece == null)
            return "Not included";
        if (pieces != null && weightPerPiece != null)
            return pieces + " \u00d7 " + weightPerPiece.intValue() + " kg";
        if (pieces != null)
            return pieces + " piece(s)";
        return weightPerPiece.intValue() + " kg";
    }

    public static double orZero(Double v) {
        return v != null ? v : 0.0;
    }

    private static String fmt(double v) {
        return String.format("%.2f", v);
    }

    private String buildSubject(BookingConfirmedEvent booking) {
        String depDate = booking.getDepartureDateTime() != null ? booking.getDepartureDateTime().format(DATE_FMT) : "";
        return String.format("Booking Confirmed for | %s | %s\u2192%s | %s",
                booking.getBookingReference(),
                booking.getDepartureAirportCode(),
                booking.getArrivalAirportCode(),
                depDate);
    }
}
