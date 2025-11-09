package com.eaglebank.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data // generates getters + setters + toString + equals/hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    private String line1;
    private String line2;
    private String line3;
    private String town;
    private String county;
    private String postcode;
}