package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.BankAccountResponse;
import com.eaglebank.model.dto.CreateBankAccountRequest;
import com.eaglebank.model.dto.UpdateBankAccountRequest;
import com.eaglebank.model.entity.BankAccount;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T14:17:46+0000",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccountResponse toResponse(BankAccount entity) {
        if ( entity == null ) {
            return null;
        }

        BankAccountResponse.BankAccountResponseBuilder bankAccountResponse = BankAccountResponse.builder();

        bankAccountResponse.accountNumber( entity.getAccountNumber() );
        bankAccountResponse.sortCode( entity.getSortCode() );
        bankAccountResponse.name( entity.getName() );
        bankAccountResponse.accountType( entity.getAccountType() );
        bankAccountResponse.balance( entity.getBalance() );
        bankAccountResponse.currency( entity.getCurrency() );
        bankAccountResponse.createdTimestamp( entity.getCreatedTimestamp() );
        bankAccountResponse.updatedTimestamp( entity.getUpdatedTimestamp() );

        return bankAccountResponse.build();
    }

    @Override
    public BankAccount toEntity(CreateBankAccountRequest request) {
        if ( request == null ) {
            return null;
        }

        BankAccount.BankAccountBuilder bankAccount = BankAccount.builder();

        bankAccount.name( request.getName() );
        bankAccount.accountType( request.getAccountType() );

        bankAccount.accountNumber( generateAccountNumber() );
        bankAccount.sortCode( "10-10-10" );
        bankAccount.balance( java.math.BigDecimal.ZERO );
        bankAccount.currency( "GBP" );
        bankAccount.createdTimestamp( now() );
        bankAccount.updatedTimestamp( now() );

        return bankAccount.build();
    }

    @Override
    public void updateEntityFromRequest(UpdateBankAccountRequest request, BankAccount entity) {
        if ( request == null ) {
            return;
        }

        entity.setName( request.getName() );
        entity.setAccountType( request.getAccountType() );

        entity.setUpdatedTimestamp( now() );
    }
}
