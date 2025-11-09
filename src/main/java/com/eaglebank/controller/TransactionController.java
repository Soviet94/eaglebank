package com.eaglebank.controller;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateTransactionRequest;
import com.eaglebank.model.dto.TransactionResponse;
import com.eaglebank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts/{accountNumber}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @PathVariable String accountNumber,
            @Valid @RequestBody CreateTransactionRequest request) {

        TransactionResponse response = transactionService.createTransaction(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> listTransactions(@PathVariable String accountNumber) {
        List<TransactionResponse> transactions = transactionService.listTransactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable String accountNumber,
            @PathVariable String transactionId) {

        TransactionResponse transaction = transactionService.getTransaction(accountNumber, transactionId);
        return ResponseEntity.ok(transaction);
    }
}
