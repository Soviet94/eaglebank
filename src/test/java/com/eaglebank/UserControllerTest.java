package com.eaglebank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eaglebank.model.dto.CreateUserRequest;
import com.eaglebank.model.dto.AddressRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .name("Alice Johnson")
                .address(AddressRequest.builder()
                        .line1("12 Green Street")
                        .town("Manchester")
                        .county("Greater Manchester")
                        .postcode("M1 1AA")
                        .build())
                .phoneNumber("07123456789")
                .email("alice.johnson@example.com")
                .build();

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
}