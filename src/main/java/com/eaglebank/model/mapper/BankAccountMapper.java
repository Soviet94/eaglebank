package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.*;
import com.eaglebank.model.entity.*;
import org.mapstruct.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccountResponse toResponse(BankAccount entity);

    @Mapping(target = "accountNumber", expression = "java(generateAccountNumber())")
    @Mapping(target = "sortCode", constant = "10-10-10")
    @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "currency", constant = "GBP")
    @Mapping(target = "createdTimestamp", expression = "java(now())")
    @Mapping(target = "updatedTimestamp", expression = "java(now())")
    BankAccount toEntity(CreateBankAccountRequest request);

    @Mapping(target = "updatedTimestamp", expression = "java(now())")
    void updateEntityFromRequest(UpdateBankAccountRequest request, @MappingTarget BankAccount entity);

    default String generateAccountNumber() {
        return "01" + String.format("%06d", (int)(Math.random() * 1000000));
    }

    default OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}
