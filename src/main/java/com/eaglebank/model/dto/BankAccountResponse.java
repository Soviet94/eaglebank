package com.eaglebank.model.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountResponse {
    private String accountNumber;
    private String sortCode;
    private String name;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
}
