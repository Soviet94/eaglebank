package com.eaglebank.model.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateBankAccountRequest {
    private String name;
    private String accountType;
}