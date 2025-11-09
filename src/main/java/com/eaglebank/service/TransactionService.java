package com.eaglebank.service;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateTransactionRequest;
import com.eaglebank.model.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(String accountNumber, CreateTransactionRequest request);
    List<TransactionResponse> listTransactions(String accountNumber);
    TransactionResponse getTransaction(String accountNumber, String transactionId);
}