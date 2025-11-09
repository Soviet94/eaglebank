package com.eaglebank.model.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private String id;
    private String name;
    private AddressRequest address;
    private String phoneNumber;
    private String email;
    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
}