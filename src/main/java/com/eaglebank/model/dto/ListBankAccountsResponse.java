package com.eaglebank.model.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListBankAccountsResponse {
    private List<BankAccountResponse> accounts;
}
