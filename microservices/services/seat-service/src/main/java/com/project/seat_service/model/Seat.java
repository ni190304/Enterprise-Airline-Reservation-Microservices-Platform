package com.project.seat_service.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.project.enums.SeatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
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
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Integer seatRow;

    private Character columnLetter;

    private SeatType seatType;

    private Double basePrice;

    private Double premiumSuperCharge;

    private Boolean isAvailable = true;

    private Boolean isBlocked = false;

    private Boolean isEmergencyExist = false;

    private Boolean isActive = true;

    private Boolean hasExtraLegroom = false;

    private Boolean hasPowerOutlet = false;

    private Boolean hasTvScreen = false;

    private Boolean hasExtraWidth = false;

    private Integer seatPitch;
    private Integer seatWidth;

    @ManyToOne
    private SeatMap seatMap;

    @ManyToOne
    private CabinClass cabinClass;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Version
    private Long version;

    public Double getTotalPrice() {
        Double total = basePrice != null ? basePrice : 0;
        if (premiumSuperCharge != null) {
            total += premiumSuperCharge;
        }
        return total;
    }

    public boolean isBookable() {
        return isActive && isAvailable && !isBlocked;
    }

    public String getFullPosition() {
        return seatRow + "" + columnLetter;
    }

}
