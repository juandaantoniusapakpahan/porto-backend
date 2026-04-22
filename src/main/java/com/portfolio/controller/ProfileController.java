package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.request.UpdateProfileRequest;
import com.portfolio.dto.response.UserResponse;
import com.portfolio.security.UserPrincipal;
import com.portfolio.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Manage own user profile")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "Get own profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(profileService.getProfile(principal.id())));
    }

    @PutMapping
    @Operation(summary = "Update own profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                profileService.updateProfile(principal.id(), request),
                "Profile updated successfully"
        ));
    }

    @DeleteMapping
    @Operation(summary = "Delete own account permanently")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(
            @AuthenticationPrincipal UserPrincipal principal) {
        profileService.deleteAccount(principal.id());
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }
}
