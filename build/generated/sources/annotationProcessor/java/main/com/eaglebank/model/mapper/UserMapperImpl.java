package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.AddressRequest;
import com.eaglebank.model.dto.CreateUserRequest;
import com.eaglebank.model.dto.UpdateUserRequest;
import com.eaglebank.model.dto.UserResponse;
import com.eaglebank.model.entity.Address;
import com.eaglebank.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T14:17:46+0000",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( entity.getId() );
        userResponse.name( entity.getName() );
        userResponse.address( addressToAddressRequest( entity.getAddress() ) );
        userResponse.phoneNumber( entity.getPhoneNumber() );
        userResponse.email( entity.getEmail() );
        userResponse.createdTimestamp( entity.getCreatedTimestamp() );
        userResponse.updatedTimestamp( entity.getUpdatedTimestamp() );

        return userResponse.build();
    }

    @Override
    public User toEntity(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( request.getName() );
        user.phoneNumber( request.getPhoneNumber() );
        user.email( request.getEmail() );
        user.address( addressRequestToAddress( request.getAddress() ) );

        user.id( generateUserId() );
        user.createdTimestamp( now() );
        user.updatedTimestamp( now() );

        return user.build();
    }

    @Override
    public void updateEntityFromRequest(UpdateUserRequest request, User entity) {
        if ( request == null ) {
            return;
        }

        entity.setName( request.getName() );
        entity.setPhoneNumber( request.getPhoneNumber() );
        entity.setEmail( request.getEmail() );
        if ( request.getAddress() != null ) {
            if ( entity.getAddress() == null ) {
                entity.setAddress( Address.builder().build() );
            }
            addressRequestToAddress1( request.getAddress(), entity.getAddress() );
        }
        else {
            entity.setAddress( null );
        }

        entity.setUpdatedTimestamp( now() );
    }

    protected AddressRequest addressToAddressRequest(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressRequest.AddressRequestBuilder addressRequest = AddressRequest.builder();

        addressRequest.line1( address.getLine1() );
        addressRequest.line2( address.getLine2() );
        addressRequest.line3( address.getLine3() );
        addressRequest.town( address.getTown() );
        addressRequest.county( address.getCounty() );
        addressRequest.postcode( address.getPostcode() );

        return addressRequest.build();
    }

    protected Address addressRequestToAddress(AddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.line1( addressRequest.getLine1() );
        address.line2( addressRequest.getLine2() );
        address.line3( addressRequest.getLine3() );
        address.town( addressRequest.getTown() );
        address.county( addressRequest.getCounty() );
        address.postcode( addressRequest.getPostcode() );

        return address.build();
    }

    protected void addressRequestToAddress1(AddressRequest addressRequest, Address mappingTarget) {
        if ( addressRequest == null ) {
            return;
        }

        mappingTarget.setLine1( addressRequest.getLine1() );
        mappingTarget.setLine2( addressRequest.getLine2() );
        mappingTarget.setLine3( addressRequest.getLine3() );
        mappingTarget.setTown( addressRequest.getTown() );
        mappingTarget.setCounty( addressRequest.getCounty() );
        mappingTarget.setPostcode( addressRequest.getPostcode() );
    }
}
