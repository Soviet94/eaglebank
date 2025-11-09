package com.eaglebank.service;

import com.eaglebank.model.dto.CreateUserRequest;
import com.eaglebank.model.dto.UpdateUserRequest;
import com.eaglebank.model.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    UserResponse createUser(CreateUserRequest request);

    @Transactional(readOnly = true)
    UserResponse fetchUserById(String userId);

    @Transactional
    UserResponse updateUser(String userId, UpdateUserRequest request);

    @Transactional
    void deleteUserById(String userId);
}
