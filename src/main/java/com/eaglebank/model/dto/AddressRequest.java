package com.eaglebank.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message = "Address line1 is required")
    private String line1;

    private String line2;
    private String line3;

    @NotBlank(message = "Town is required")
    private String town;

    @NotBlank(message = "County is required")
    private String county;

    @NotBlank(message = "Postcode is required")
    private String postcode;
}
