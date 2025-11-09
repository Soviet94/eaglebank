package com.eaglebank.service;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateBankAccountRequest;
import com.eaglebank.model.dto.ListBankAccountsResponse;
import com.eaglebank.model.dto.UpdateBankAccountRequest;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {
    @Transactional
    BankAccountResponse createBankAccount(CreateBankAccountRequest request);

    @Transactional(readOnly = true)
    ListBankAccountsResponse listAccounts();

    @Transactional(readOnly = true)
    BankAccountResponse getAccountByAccountNumber(String accountNumber);

    @Transactional
    BankAccountResponse updateBankAccount(String accountNumber, UpdateBankAccountRequest request);

    @Transactional
    void deleteBankAccount(String accountNumber);
}