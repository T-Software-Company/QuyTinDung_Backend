package com.tsoftware.qtd.dto.response;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    String profileId; // ID của hồ sơ
    String userId; // ID người dùng từ hệ thống xác thực (ví dụ: Keycloak)
    String email; // Email người dùng
    String username; // Tên người dùng
    String firstName; // Tên
    String lastName; // Họ

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob; // Ngày sinh, định dạng theo ISO
}
