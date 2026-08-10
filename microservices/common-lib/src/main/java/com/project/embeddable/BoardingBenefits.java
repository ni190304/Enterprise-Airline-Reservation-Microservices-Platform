package com.project.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardingBenefits {

    @Column(name = "priority boarding", nullable = false)
    @Builder.Default
    private Boolean priorityBoarding = false;

    @Column(name = "priority_checkin", nullable = false)
    @Builder.Default
    private Boolean priorityCheckin = false;

    @Column(name = "fast track security", nullable = false)
    @Builder.Default
    private Boolean fastTrackSecurity = false;

}
