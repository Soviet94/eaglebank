package com.eaglebank.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount {
    @Id
    private String accountNumber;

    private String sortCode;
    private String name;
    private String accountType;
    private BigDecimal balance;
    private String currency;

    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;

    @ManyToOne
    private User user;
}
