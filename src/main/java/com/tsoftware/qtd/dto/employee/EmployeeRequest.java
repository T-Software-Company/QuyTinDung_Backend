package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
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
public class EmployeeRequest {

  @NotBlank(message = "USERNAME_REQUIRED")
  @Size(min = 4, message = "INVALID_USERNAME")
  String username;

  @NotBlank(message = "PASSWORD_REQUIRED")
  @Size(min = 6, message = "INVALID_PASSWORD")
  String password;

  @NotBlank(message = "EMAIL_REQUIRED")
  @Email(message = "INVALID_EMAIL_FORMAT")
  String email;

  @Valid AddressDto address;

  @NotBlank(message = "FIRST_NAME_REQUIRED")
  String firstName;

  @NotBlank(message = "LAST_NAME_REQUIRED")
  String lastName;

  List<String> roles;

  @NotNull(message = "DOB_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Past(message = "DOB_MUST_BE_IN_PAST")
  LocalDate dayOfBirth;

  @NotNull(message = "GENDER_REQUIRED")
  Gender gender;

  @NotNull(message = "EMPLOYMENT_STATUS_REQUIRED")
  @Pattern(
      regexp = "WORKING|ON_LEAVE|RESIGNED",
      message = "Invalid employmentStatus value should is (WORKING|ON_LEAVE|RESIGNED)")
  String employmentStatus;

  @NotNull(message = "BANNED_STATUS_REQUIRED")
  @Pattern(regexp = "ACTIVE|LOCKED", message = "Invalid banned value should is (ACTIVE|LOCKED)")
  String banned;

  @NotBlank(message = "PHONE_REQUIRED")
  @Size(max = 15, message = "PHONE_TOO_LONG")
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "INVALID_PHONE_FORMAT")
  String phone;
}
