package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.service.ProfileService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // Tự động tạo constructor cho các trường được đánh dấu final
@FieldDefaults(
    level = AccessLevel.PRIVATE,
    makeFinal = true) // Thiết lập các trường dữ liệu là private final
@Slf4j // Tạo logger cho việc ghi log
public class ProfileController {

  ProfileService profileService; // Inject ProfileService để sử dụng trong controller

  // Endpoint đăng ký người dùng mới
  @PostMapping("/register")
  ApiResponse<ProfileResponse> register(@RequestBody @Valid RegistrationRequest request) {
    return ApiResponse.<ProfileResponse>builder()
        .result(profileService.register(request)) // Gọi service để xử lý đăng ký và trả về kết quả
        .build();
  }

  // Endpoint lấy danh sách tất cả hồ sơ người dùng
  @GetMapping("/profiles")
  ApiResponse<List<ProfileResponse>> getAllProfiles() {
    return ApiResponse.<List<ProfileResponse>>builder()
        .result(profileService.getAllProfiles()) // Gọi service để lấy danh sách tất cả hồ sơ
        .build();
  }

  // Endpoint lấy hồ sơ của người dùng hiện tại
  @GetMapping("/my-profile")
  ApiResponse<ProfileResponse> getMyProfiles() {
    return ApiResponse.<ProfileResponse>builder()
        .result(profileService.getMyProfile()) // Gọi service để lấy hồ sơ của người dùng hiện tại
        .build();
  }
}
