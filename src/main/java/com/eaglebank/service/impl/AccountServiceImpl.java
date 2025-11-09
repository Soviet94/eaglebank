package com.eaglebank.service.impl;

import com.eaglebank.exception.AccessDeniedException;
import com.eaglebank.exception.ResourceNotFoundException;
import com.eaglebank.model.dto.*;
import com.eaglebank.model.entity.BankAccount;
import com.eaglebank.model.entity.User;
import com.eaglebank.repository.BankAccountRepository;
import com.eaglebank.repository.UserRepository;
import com.eaglebank.security.SecurityUtils;
import com.eaglebank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public BankAccountResponse createBankAccount(CreateBankAccountRequest request) {
        String userId = securityUtils.getAuthenticatedUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialDeposit() != null ? request.getInitialDeposit() : BigDecimal.ZERO);
        account.setCreatedTimestamp(OffsetDateTime.now());

        bankAccountRepository.save(account);

        return BankAccountResponse.builder()
                .createdTimestamp(account.getCreatedTimestamp())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ListBankAccountsResponse listAccounts() {
        String userId = securityUtils.getAuthenticatedUserId();

        var accounts = bankAccountRepository.findByUserId(userId);

        var accountResponses = accounts.stream()
                .map(account -> BankAccountResponse.builder()
                        .accountNumber(account.getAccountNumber())
                        .sortCode(account.getSortCode())
                        .name(account.getName())
                        .accountType(account.getAccountType())
                        .balance(account.getBalance())
                        .currency(account.getCurrency())
                        .createdTimestamp(account.getCreatedTimestamp())
                        .updatedTimestamp(account.getUpdatedTimestamp())
                        .build())
                .collect(Collectors.toList());

        return ListBankAccountsResponse.builder()
                .accounts(accountResponses)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountResponse getAccountByAccountNumber(String accountNumber) {
        String userId = securityUtils.getAuthenticatedUserId();

        var account = bankAccountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to view this bank account");
        }

        return BankAccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .sortCode(account.getSortCode())
                .name(account.getName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .createdTimestamp(account.getCreatedTimestamp())
                .updatedTimestamp(account.getUpdatedTimestamp())
                .build();
    }

    @Override
    @Transactional
    public BankAccountResponse updateBankAccount(String accountNumber, UpdateBankAccountRequest request) {
        String userId = securityUtils.getAuthenticatedUserId();

        var account = bankAccountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this bank account");
        }

        boolean updated = false;

        // Apply partial updates
        if (request.getName() != null && !request.getName().isBlank()) {
            account.setName(request.getName());
            updated = true;
        }

        if (request.getAccountType() != null && !request.getAccountType().isBlank()) {
            account.setAccountType(request.getAccountType());
            updated = true;
        }

        if (updated) {
            account.setUpdatedTimestamp(OffsetDateTime.now());
            bankAccountRepository.save(account);
        }

        return BankAccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .sortCode(account.getSortCode())
                .name(account.getName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .createdTimestamp(account.getCreatedTimestamp())
                .updatedTimestamp(account.getUpdatedTimestamp())
                .build();
    }

    @Override
    @Transactional
    public void deleteBankAccount(String accountNumber) {
        String userId = securityUtils.getAuthenticatedUserId();

        BankAccount account = bankAccountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        // Ensure user can only delete their own account
        if (!account.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this bank account");
        }

        bankAccountRepository.delete(account);
    }

}
