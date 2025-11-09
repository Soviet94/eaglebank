package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.CreateTransactionRequest;
import com.eaglebank.model.dto.TransactionResponse;
import com.eaglebank.model.entity.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T14:20:00+0000",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionResponse toResponse(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionResponse.TransactionResponseBuilder transactionResponse = TransactionResponse.builder();

        transactionResponse.id( entity.getId() );
        transactionResponse.amount( entity.getAmount() );
        transactionResponse.currency( entity.getCurrency() );
        transactionResponse.type( entity.getType() );
        transactionResponse.reference( entity.getReference() );
        transactionResponse.createdTimestamp( entity.getCreatedTimestamp() );

        return transactionResponse.build();
    }

    @Override
    public Transaction toEntity(CreateTransactionRequest request) {
        if ( request == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.amount( request.getAmount() );
        transaction.currency( request.getCurrency() );
        transaction.type( request.getType() );
        transaction.reference( request.getReference() );

        transaction.id( generateTransactionId() );
        transaction.createdTimestamp( now() );

        return transaction.build();
    }
}
