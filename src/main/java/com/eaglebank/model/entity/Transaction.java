package com.eaglebank.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {
    @Id
    private String id;

    private BigDecimal amount;
    private String currency;
    private String type;
    private String reference;
    private OffsetDateTime createdTimestamp;

    @ManyToOne
    private BankAccount bankAccount;
}
