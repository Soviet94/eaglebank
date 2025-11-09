package com.eaglebank;

import com.eaglebank.controller.AccountController;
import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateBankAccountRequest;
import com.eaglebank.model.dto.ListBankAccountsResponse;
import com.eaglebank.model.dto.UpdateBankAccountRequest;
import com.eaglebank.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateBankAccount() throws Exception {
		CreateBankAccountRequest request = CreateBankAccountRequest.builder()
				.name("Test Account")
				.accountType("SAVINGS")
				.build();

		BankAccountResponse response = BankAccountResponse.builder()
				.accountNumber("01123456")
				.name("Test Account")
				.balance(BigDecimal.ZERO)
				.currency("GBP")
				.createdTimestamp(OffsetDateTime.now())
				.build();

		Mockito.when(accountService.createBankAccount(Mockito.any())).thenReturn(response);

		mockMvc.perform(post("/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.accountNumber").value("01123456"))
				.andExpect(jsonPath("$.name").value("Test Account"));
	}

	@Test
	void testListAccounts() throws Exception {
		ListBankAccountsResponse response = new ListBankAccountsResponse(
				Collections.singletonList(BankAccountResponse.builder()
						.accountNumber("01123456")
						.name("Test Account")
						.balance(BigDecimal.ZERO)
						.currency("GBP")
						.createdTimestamp(OffsetDateTime.now())
						.build())
		);

		Mockito.when(accountService.listAccounts()).thenReturn(response);

		mockMvc.perform(get("/v1/accounts"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accounts[0].accountNumber").value("01123456"));
	}

	@Test
	void testGetAccount() throws Exception {
		BankAccountResponse response = BankAccountResponse.builder()
				.accountNumber("01123456")
				.name("Test Account")
				.balance(BigDecimal.ZERO)
				.currency("GBP")
				.createdTimestamp(OffsetDateTime.now())
				.build();

		Mockito.when(accountService.getAccountByAccountNumber("01123456")).thenReturn(response);

		mockMvc.perform(get("/v1/accounts/01123456"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNumber").value("01123456"));
	}

	@Test
	void testUpdateAccount() throws Exception {
		UpdateBankAccountRequest request = UpdateBankAccountRequest.builder()
				.name("Updated Account")
				.build();

		BankAccountResponse response = BankAccountResponse.builder()
				.accountNumber("01123456")
				.name("Updated Account")
				.balance(BigDecimal.ZERO)
				.currency("GBP")
				.createdTimestamp(OffsetDateTime.now())
				.build();

		Mockito.when(accountService.updateBankAccount(Mockito.eq("01123456"), Mockito.any()))
				.thenReturn(response);

		mockMvc.perform(patch("/v1/accounts/01123456")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Account"));
	}

	@Test
	void testDeleteAccount() throws Exception {
		Mockito.doNothing().when(accountService).deleteBankAccount("01123456");

		mockMvc.perform(delete("/v1/accounts/01123456"))
				.andExpect(status().isNoContent());
	}
}