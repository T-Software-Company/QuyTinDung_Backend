package com.tsoftware.qtd.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.request.ProfileUpdateRequest;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.service.ProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor // Tự động tạo constructor cho các trường được đánh dấu final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // Thiết lập các trường dữ liệu là private final
@Slf4j // Tạo logger cho việc ghi log
public class ProfileController {

    ProfileService profileService; // Inject ProfileService để sử dụng trong controller

    // Endpoint đăng ký người dùng mới
    @PostMapping("/register")
    ApiResponse<ProfileResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.registerProfile(request)) // Gọi service để xử lý đăng ký và trả về kết quả
                .build();
    }

    // Endpoint lấy danh sách tất cả hồ sơ người dùng
    @GetMapping("/profiles")
    ApiResponse<List<ProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfiles())
                .build();
    }

    // Endpoint lấy hồ sơ của người dùng hiện tại
    @GetMapping("/my-profile")
    ApiResponse<ProfileResponse> getMyProfiles() {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getMyProfile())
                .build();
    }

    // Endpoint để cập nhật hồ sơ của người dùng hiện tại
    @PutMapping("/my-profile")
    public ApiResponse<Void> updateMyProfile(@RequestBody @Valid ProfileUpdateRequest request) {
        profileService.updateProfile(request);
        return ApiResponse.<Void>builder().build();
    }

    // Cập nhật hồ sơ của người dùng bởi quản trị viên
    @PutMapping("/users/{userId}/profile")
    public ApiResponse<Void> updateProfileByUserId(
            @PathVariable String userId, @RequestBody @Valid ProfileUpdateRequest request) {
        profileService.updateProfileByAdmin(userId, request); //
        return ApiResponse.<Void>builder().build();
    }

    // Reset mật khẩu của người dùng
    @PutMapping("/users/{userId}/reset-password")
    public ApiResponse<Void> resetPassword(@PathVariable String userId, @RequestBody Map<String, Object> request) {
        String newPassword = (String) request.get("newPassword");
        profileService.resetPassword(userId, newPassword); // Call the service to reset password
        return ApiResponse.<Void>builder().build(); // Return an empty response with status 204
    }

    // Bật tài khoản người dùng
    @PutMapping("/users/{userId}/activate")
    public ApiResponse<Void> activateUser(@PathVariable String userId) {
        profileService.activateUser(userId);
        return ApiResponse.<Void>builder().build();
    }

    // Vô hiệu hóa tài khoản người dùng
    @PutMapping("/users/{userId}/deactivate")
    public ApiResponse<Void> deactivateUser(@PathVariable String userId) {
        profileService.deactivateUser(userId);
        return ApiResponse.<Void>builder().build();
    }
}
