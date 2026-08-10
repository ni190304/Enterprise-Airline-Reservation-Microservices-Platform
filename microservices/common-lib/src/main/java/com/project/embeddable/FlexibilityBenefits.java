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
public class FlexibilityBenefits {

    private Boolean freeDateChange = false;

    @Column(name = "partial refund", nullable = false)
    @Builder.Default
    private Boolean partialRefund = false;

    @Column(name = "full refund", nullable = false)
    @Builder.Default
    private Boolean fullRefund = false;

}
