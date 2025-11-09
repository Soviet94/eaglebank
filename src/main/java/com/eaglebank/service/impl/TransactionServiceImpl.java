package com.eaglebank.service.impl;

import com.eaglebank.exception.AccessDeniedException;
import com.eaglebank.exception.ResourceNotFoundException;
import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateTransactionRequest;
import com.eaglebank.model.dto.TransactionResponse;
import com.eaglebank.model.entity.BankAccount;
import com.eaglebank.model.entity.Transaction;
import com.eaglebank.repository.BankAccountRepository;
import com.eaglebank.repository.TransactionRepository;
import com.eaglebank.security.SecurityUtils;
import com.eaglebank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final BankAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public TransactionResponse createTransaction(String accountNumber, CreateTransactionRequest request) {
        BankAccount account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        String userId = securityUtils.getAuthenticatedUserId();
        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to modify this account");
        }

        if ("DEPOSIT".equalsIgnoreCase(request.getType())) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        } else if ("WITHDRAWAL".equalsIgnoreCase(request.getType())) {
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        } else {
            throw new IllegalArgumentException("Unsupported transaction type");
        }

        account.setUpdatedTimestamp(OffsetDateTime.now());
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .type(request.getType())
                .reference(request.getReference())
                .createdTimestamp(OffsetDateTime.now())
                .bankAccount(account)
                .build();
        transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .type(request.getType())
                .createdTimestamp(transaction.getCreatedTimestamp())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> listTransactions(String accountNumber) {
        BankAccount account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        String userId = securityUtils.getAuthenticatedUserId();
        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to view this account's transactions");
        }

        return transactionRepository.findByBankAccountOrderByCreatedTimestampDesc(account);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(String accountNumber, String transactionId) {
        BankAccount account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        String userId = securityUtils.getAuthenticatedUserId();

        // Only allow access if the current user owns the account
        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to view this transaction");
        }

        Transaction tx = transactionRepository.findByBankAccountAccountNumberAndId(accountNumber, transactionId);

        if (tx == null){
            throw new ResourceNotFoundException("Transaction not found");
        }

        return TransactionResponse.builder()
                .id(tx.getId())
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .type(tx.getType())
                .reference(tx.getReference())
                .userId(tx.getBankAccount().getUser().getId())
                .createdTimestamp(tx.getCreatedTimestamp())
                .build();
    }
}
