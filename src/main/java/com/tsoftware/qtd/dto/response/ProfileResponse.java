package com.tsoftware.qtd.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;

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
    List<RoleResponse> roles; // Danh sách các vai trò của người dùng

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob; // Ngày sinh, định dạng theo ISO

    String phone; // Số điện thoại của người dùng
    String address; // Địa chỉ của người dùng
    String employee_code; // Mã nhân viên của người dùng

    Gender gender; // Giới tính của người dùng (Nam/Nữ)
    EmploymentStatus employmentStatus; // Trạng thái làm việc (Đang làm việc/Đã xin nghỉ)
    Banned banned; // Trạng thái khóa (Đã khóa/Chưa khóa)
}
