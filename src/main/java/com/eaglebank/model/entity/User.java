package com.eaglebank.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    private String id;

    private String name;
    private String phoneNumber;
    private String email;

    @Embedded
    private Address address;

    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
}
