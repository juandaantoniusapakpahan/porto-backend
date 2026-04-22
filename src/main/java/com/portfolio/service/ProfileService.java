package com.portfolio.service;

import com.portfolio.dto.request.UpdateProfileRequest;
import com.portfolio.dto.response.UserResponse;
import com.portfolio.entity.User;
import com.portfolio.exception.DuplicateResourceException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.UserMapper;
import com.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    @Transactional(readOnly = true)
    public UserResponse getProfile(UUID userId) {
        User user = findUserById(userId);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        User user = findUserById(userId);

        if (request.username() != null && !request.username().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.username())) {
                throw new DuplicateResourceException("Username already taken: " + request.username());
            }
            user.setUsername(request.username());
        }

        if (request.avatarUrl() != null) {
            user.setAvatarUrl(request.avatarUrl());
        }

        user = userRepository.save(user);
        log.info("Profile updated for user: {}", user.getUsername());
        return userMapper.toResponse(user);
    }

    @Transactional
    public void deleteAccount(UUID userId) {
        User user = findUserById(userId);
        refreshTokenService.revokeAllForUser(user);
        userRepository.delete(user);
        log.info("Account deleted for userId: {}", userId);
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }
}
