package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.*;
import com.eaglebank.model.entity.*;
import org.mapstruct.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "userId", ignore = true)
    TransactionResponse toResponse(Transaction entity);

    @Mapping(target = "id", expression = "java(generateTransactionId())")
    @Mapping(target = "createdTimestamp", expression = "java(now())")
    @Mapping(target = "bankAccount", ignore = true)
    Transaction toEntity(CreateTransactionRequest request);

    default String generateTransactionId() {
        return "tan-" + UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    default OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}
