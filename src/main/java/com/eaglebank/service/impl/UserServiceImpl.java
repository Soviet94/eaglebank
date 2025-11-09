package com.eaglebank.service.impl;

import com.eaglebank.exception.AccessDeniedException;
import com.eaglebank.exception.ConflictException;
import com.eaglebank.exception.ResourceNotFoundException;
import com.eaglebank.model.dto.CreateUserRequest;
import com.eaglebank.model.dto.UpdateUserRequest;
import com.eaglebank.model.dto.UserResponse;
import com.eaglebank.model.entity.User;
import com.eaglebank.model.mapper.UserMapper;
import com.eaglebank.repository.BankAccountRepository;
import com.eaglebank.repository.UserRepository;
import com.eaglebank.security.SecurityUtils;
import com.eaglebank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserMapper userMapper;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User entity = userMapper.toEntity(request);
        userRepository.save(entity);
        return userMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse fetchUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));

        String currentUserId = securityUtils.getAuthenticatedUserId();
        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("You are not allowed to access this user's details");
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        String currentUserId = securityUtils.getAuthenticatedUserId();

        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this user's details");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());

        if (request.getAddress() != null) {
            user.getAddress().setLine1(request.getAddress().getLine1());
            user.getAddress().setLine2(request.getAddress().getLine2());
            user.getAddress().setLine3(request.getAddress().getLine3());
            user.getAddress().setTown(request.getAddress().getTown());
            user.getAddress().setCounty(request.getAddress().getCounty());
            user.getAddress().setPostcode(request.getAddress().getPostcode());
        }

        user.setUpdatedTimestamp(java.time.OffsetDateTime.now());

        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserById(String userId) {
        String currentUserId = securityUtils.getAuthenticatedUserId();

        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this user's details");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));

        boolean hasAccounts = bankAccountRepository.existsByUserId(userId);
        if (hasAccounts) {
            throw new ConflictException("A user cannot be deleted when they are associated with a bank account");
        }

        userRepository.delete(user);
    }
}
