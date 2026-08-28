package com.project.booking_service.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.embeddable.ContactInfo;
import com.project.enums.BookingStatus;
import com.project.enums.CabinClassType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookingReference;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long flightInstanceId;

    @Column(nullable = false)
    private Long airlineId;

    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass = CabinClassType.ECONOMY;

    @Column(nullable = false)
    private Long fareId;

    private boolean flexibleTicket;
    private LocalDateTime ticketTimeLimit;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Passenger> passengers = new HashSet<>();

    @ElementCollection
    private List<Long> seatInstanceIds;

    @ElementCollection
    private List<Long> ancillaryIds;

    @ElementCollection
    private List<Long> mealIds;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @CreatedDate
    private Instant bookingDate;

    @UpdateTimestamp
    private Instant lastModified;

    private boolean ticketIssued;

    @Embedded
    private ContactInfo contactInfo;

}
