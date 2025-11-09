package com.eaglebank.controller;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateBankAccountRequest;
import com.eaglebank.model.dto.ListBankAccountsResponse;
import com.eaglebank.model.dto.UpdateBankAccountRequest;
import com.eaglebank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<BankAccountResponse> createBankAccount(
            @Valid @RequestBody CreateBankAccountRequest request) {
        BankAccountResponse response = accountService.createBankAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ListBankAccountsResponse> listAccounts() {
        ListBankAccountsResponse response = accountService.listAccounts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable String accountNumber) {
        BankAccountResponse response = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> updateAccount(
            @PathVariable String accountNumber,
            @Valid @RequestBody UpdateBankAccountRequest request) {

        BankAccountResponse response = accountService.updateBankAccount(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteBankAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
