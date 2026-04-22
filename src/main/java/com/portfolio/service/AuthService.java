package com.portfolio.service;

import com.portfolio.dto.request.LoginRequest;
import com.portfolio.dto.request.RefreshTokenRequest;
import com.portfolio.dto.request.RegisterRequest;
import com.portfolio.dto.response.AuthResponse;
import com.portfolio.dto.response.UserResponse;
import com.portfolio.entity.RefreshToken;
import com.portfolio.entity.User;
import com.portfolio.exception.DuplicateResourceException;
import com.portfolio.mapper.UserMapper;
import com.portfolio.repository.UserRepository;
import com.portfolio.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already registered: " + request.email());
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username already taken: " + request.username());
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        user = userRepository.save(user);
        log.info("New user registered: {}", user.getUsername());

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.usernameOrEmail(), request.password())
        );

        User user = userRepository.findByEmailOrUsername(request.usernameOrEmail(), request.usernameOrEmail())
                .orElseThrow();

        log.info("User logged in: {}", user.getUsername());
        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.refreshToken());
        User user = refreshToken.getUser();

        // Rotate refresh token
        refreshTokenService.revokeRefreshToken(request.refreshToken());
        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        refreshTokenService.revokeRefreshToken(refreshTokenValue);
        log.debug("User logged out, refresh token revoked");
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        UserResponse userResponse = userMapper.toResponse(user);

        return AuthResponse.of(
                accessToken,
                refreshToken.getToken(),
                jwtUtil.getAccessTokenExpiration() / 1000,
                userResponse
        );
    }
}
