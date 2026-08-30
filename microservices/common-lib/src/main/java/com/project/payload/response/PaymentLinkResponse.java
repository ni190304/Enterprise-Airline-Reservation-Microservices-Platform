package com.project.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLinkResponse {

    private Long id;

    private String payment_link_url;
    private String payment_link_id;

}
