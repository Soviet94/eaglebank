package com.eaglebank.model.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BadRequestErrorResponse {
    private String message;
    private List<FieldErrorDetail> details;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FieldErrorDetail {
        private String field;
        private String message;
        private String type;
    }
}
