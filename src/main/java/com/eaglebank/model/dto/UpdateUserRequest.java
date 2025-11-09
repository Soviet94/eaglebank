package com.eaglebank.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String name;

    @Valid
    private AddressRequest address;

    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format (e.g. +441234567890)")
    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;
}