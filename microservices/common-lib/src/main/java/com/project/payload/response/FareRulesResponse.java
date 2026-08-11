package com.project.payload.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareRulesResponse {

    private Long id;
    private String ruleName;
    private Long fareId;
    private Long airlineId;
    private Boolean isRefundable;
    private Double changeFee;
    private Double cancellationFee;
    private Integer refundDeadlineDays;
    private Integer changeDeadlineHours;
    private Boolean isChangeable;
    private Instant createdAt;
    private Instant updatedAt;

}
