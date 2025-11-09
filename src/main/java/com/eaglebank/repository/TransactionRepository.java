package com.eaglebank.repository;

import com.eaglebank.model.dto.TransactionResponse;
import com.eaglebank.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    // Get all transactions for a given BankAccount entity
    List<TransactionResponse> findByBankAccountOrderByCreatedTimestampDesc(BankAccount bankAccount);

    Transaction findByBankAccountAccountNumberAndId(String accountNumber, String transactionId);
}