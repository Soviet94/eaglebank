package com.eaglebank.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateBankAccountRequest {
    @NotBlank
    private String name;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Initial deposit is required")
    private BigDecimal initialDeposit;
}
