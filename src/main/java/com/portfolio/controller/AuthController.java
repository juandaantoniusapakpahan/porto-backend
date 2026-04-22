package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.request.LoginRequest;
import com.portfolio.dto.request.RefreshTokenRequest;
import com.portfolio.dto.request.RegisterRequest;
import com.portfolio.dto.response.AuthResponse;
import com.portfolio.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, logout, and token refresh")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user with email and password")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(response, "User registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive access + refresh tokens")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token using a valid refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refresh(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Token refreshed successfully"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Invalidate refresh token (logout)")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/oauth2/github")
    @Operation(summary = "Initiate GitHub OAuth2 login — redirects to GitHub authorization page")
    public ResponseEntity<Void> githubLogin() {
        // Spring Security OAuth2 handles this redirect automatically at /oauth2/authorization/github
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/oauth2/authorization/github")
                .build();
    }
}
