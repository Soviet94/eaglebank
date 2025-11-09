package com.eaglebank.model.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListTransactionsResponse {
    private List<TransactionResponse> transactions;
}
