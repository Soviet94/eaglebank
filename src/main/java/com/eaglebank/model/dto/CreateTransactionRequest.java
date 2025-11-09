package com.eaglebank.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateTransactionRequest {
    @DecimalMin("0.00")
    @DecimalMax("10000.00")
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String type;

    private String reference;
}
