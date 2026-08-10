package com.project.embeddable;

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
public class SeatBenefits {

    private Boolean extraSeatSpace = false;
    private Boolean preferredSeatChoice = false;
    private Boolean advanceSeatSelection = false;
    private Boolean guaranteedSeatTogether = false;

}
