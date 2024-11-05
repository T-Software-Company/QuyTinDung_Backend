package com.tsoftware.qtd.dto.request;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

  @NotBlank(message = "USERNAME_REQUIRED")
  @Size(min = 4, message = "INVALID_USERNAME")
  String username; // Tên người dùng, ít nhất 4 ký tự

  @NotBlank(message = "PASSWORD_REQUIRED")
  @Size(min = 6, message = "INVALID_PASSWORD")
  String password; // Mật khẩu, ít nhất 6 ký tự

  @NotBlank(message = "EMAIL_REQUIRED")
  @Email(message = "INVALID_EMAIL_FORMAT")
  String email; // Email người dùng

  @NotBlank(message = "FIRST_NAME_REQUIRED")
  String firstName; // Tên

  @NotBlank(message = "LAST_NAME_REQUIRED")
  String lastName; // Họ

  @NotBlank(message = "ROLE_NAME_REQUIRED")
  String roleName; // Role name, e.g., "ADMIN"

  @NotBlank(message = "ROLE_ID_REQUIRED")
  String roleId; // Role ID, required for role assignment

  @NotNull(message = "DOB_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Past(message = "DOB_MUST_BE_IN_PAST")
  LocalDate dob; // Ngày sinh, phải ở quá khứ

  @NotNull(message = "GENDER_REQUIRED")
  Gender gender; // Giới tính, không thể null

  @NotNull(message = "EMPLOYMENT_STATUS_REQUIRED")
  EmploymentStatus employmentStatus; // Trạng thái làm việc

  @NotNull(message = "BANNED_STATUS_REQUIRED")
  Banned banned; // Trạng thái khóa

  @NotBlank(message = "PHONE_REQUIRED")
  @Size(max = 15, message = "PHONE_TOO_LONG")
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "INVALID_PHONE_FORMAT")
  String phone; // Số điện thoại của người dùng

  @NotBlank(message = "ADDRESS_REQUIRED")
  @Size(max = 100, message = "ADDRESS_TOO_LONG")
  String address; // Địa chỉ người dùng, tối đa 100 ký tự

  @NotBlank(message = "EMPLOYEE_CODE_REQUIRED")
  @Size(max = 20, message = "EMPLOYEE_CODE_TOO_LONG")
  String employee_code; // Mã nhân viên, tối đa 20 ký tự
}
