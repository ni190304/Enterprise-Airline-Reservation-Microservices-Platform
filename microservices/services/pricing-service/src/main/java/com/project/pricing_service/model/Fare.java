package com.project.pricing_service.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.embeddable.BoardingBenefits;
import com.project.embeddable.FlexibilityBenefits;
import com.project.embeddable.InFlightBenefits;
import com.project.embeddable.PremiumServiceBenefits;
import com.project.embeddable.SeatBenefits;
import com.project.enums.CabinClassType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fare")
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Character rbdCode;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass;

    @Column(nullable = false)
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;

    @Column(nullable = false)
    private Double currentPrice;

    private String fareLabel;

    @OneToOne(mappedBy = "fare", cascade = CascadeType.ALL, orphanRemoval = true)
    private BaggagePolicy baggagePolicy;

    @OneToOne(mappedBy = "fare", cascade = CascadeType.ALL, orphanRemoval = true)
    private FareRules fareRules;

    @Embedded
    private SeatBenefits seatBenefits = new SeatBenefits();

    @Embedded
    private BoardingBenefits boardingBenefits = new BoardingBenefits();

    @Embedded
    private InFlightBenefits inFlightBenefits = new InFlightBenefits();

    @Embedded
    private FlexibilityBenefits flexibilityBenefits = new FlexibilityBenefits();

    @Embedded
    private PremiumServiceBenefits premiumServiceBenefits = new PremiumServiceBenefits();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Double getTotalPrice(){
        return baseFare+taxesAndFees+airlineFees+currentPrice;
    }

}
