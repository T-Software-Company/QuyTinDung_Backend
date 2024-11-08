package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.profile.ProfileRequest;
import com.tsoftware.qtd.dto.profile.ProfileRequestForAdmin;
import com.tsoftware.qtd.dto.profile.ProfileResponse;
import com.tsoftware.qtd.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/profiles")
public class ProfileController {

  ProfileService profileService;

  @PostMapping("/register")
  ApiResponse<ProfileResponse> register(@RequestBody @Valid ProfileRequestForAdmin request) {
    return ApiResponse.<ProfileResponse>builder()
        .result(profileService.registerProfile(request))
        .build();
  }

  @GetMapping
  ApiResponse<List<ProfileResponse>> getAllProfiles() {
    return ApiResponse.<List<ProfileResponse>>builder()
        .result(profileService.getAllProfiles())
        .build();
  }

  @GetMapping("client/my-profile")
  ApiResponse<ProfileResponse> getMyProfiles() {
    return ApiResponse.<ProfileResponse>builder().result(profileService.getMyProfile()).build();
  }

  @PutMapping("client/my-profile")
  public ApiResponse<Void> updateMyProfile(@RequestBody @Valid ProfileRequest request) {
    profileService.updateProfile(request);
    return ApiResponse.<Void>builder().build();
  }

  @PutMapping("/{userId}")
  public ApiResponse<Void> updateProfileByUserId(
      @PathVariable String userId, @RequestBody @Valid ProfileRequestForAdmin request) {
    profileService.updateProfileByAdmin(userId, request); //
    return ApiResponse.<Void>builder().build();
  }

  @PutMapping("client/reset-password")
  public ApiResponse<Void> resetPasswordForCurrentUser(@RequestBody Map<String, Object> request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    String newPassword = (String) request.get("newPassword");
    profileService.resetPassword(userId, newPassword);
    return ApiResponse.<Void>builder().build();
  }

  @PutMapping("/{userId}/reset-password")
  public ApiResponse<Void> resetPasswordForUserByAdmin(
      @PathVariable String userId, @RequestBody Map<String, Object> request) {
    String newPassword = (String) request.get("newPassword");
    profileService.resetPassword(userId, newPassword);
    return ApiResponse.<Void>builder().build();
  }

  @PutMapping("/{userId}/activate")
  public ApiResponse<Void> activateUser(@PathVariable String userId) {
    profileService.activateUser(userId);
    return ApiResponse.<Void>builder().build();
  }

  @PutMapping("/{userId}/deactivate")
  public ApiResponse<Void> deactivateUser(@PathVariable String userId) {
    profileService.deactivateUser(userId);
    return ApiResponse.<Void>builder().build();
  }
}
