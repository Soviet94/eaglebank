package com.eaglebank.repository;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    boolean existsByUserId(String userId);
    List<BankAccount> findByUserId(String userId);
    BankAccountResponse getAccountByAccountNumber(String accountNumber);
}