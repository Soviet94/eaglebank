package com.eaglebank.model.mapper;

import com.eaglebank.model.dto.*;
import com.eaglebank.model.entity.*;
import org.mapstruct.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User entity);

    @Mapping(target = "id", expression = "java(generateUserId())")
    @Mapping(target = "createdTimestamp", expression = "java(now())")
    @Mapping(target = "updatedTimestamp", expression = "java(now())")
    User toEntity(CreateUserRequest request);

    @Mapping(target = "updatedTimestamp", expression = "java(now())")
    void updateEntityFromRequest(UpdateUserRequest request, @MappingTarget User entity);

    default String generateUserId() {
        return "usr-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    default OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}
