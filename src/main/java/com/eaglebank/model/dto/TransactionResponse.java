package com.eaglebank.model.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionResponse {
    private String id;
    private BigDecimal amount;
    private String currency;
    private String type;
    private String reference;
    private String userId;
    private OffsetDateTime createdTimestamp;
}
