package com.portfolio.service;

import com.portfolio.entity.RefreshToken;
import com.portfolio.entity.User;
import com.portfolio.exception.InvalidTokenException;
import com.portfolio.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // Revoke all existing tokens for this user (single session per user)
        refreshTokenRepository.revokeAllByUser(user);

        RefreshToken token = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(token);
    }

    @Transactional(readOnly = true)
    public RefreshToken validateRefreshToken(String tokenValue) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found"));

        if (token.isRevoked()) {
            throw new InvalidTokenException("Refresh token has been revoked");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Refresh token has expired");
        }

        return token;
    }

    @Transactional
    public void revokeRefreshToken(String tokenValue) {
        refreshTokenRepository.findByToken(tokenValue).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    @Transactional
    public void revokeAllForUser(User user) {
        refreshTokenRepository.revokeAllByUser(user);
    }

    @Scheduled(cron = "0 0 3 * * *") // Daily at 3 AM
    @Transactional
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredAndRevoked(LocalDateTime.now());
        log.info("Expired and revoked refresh tokens cleaned up");
    }
}
