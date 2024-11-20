package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.List;
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
public class EmployeeRequest {

  @NotBlank
  @Size(min = 4)
  String username;

  @NotBlank
  @Size(min = 6)
  String password;

  @NotBlank @Email String email;

  @Valid AddressDto address;

  @NotBlank String firstName;

  @NotBlank String lastName;

  List<Role> roles;

  @NotNull @Past ZonedDateTime dayOfBirth;

  @NotNull Gender gender;

  @NotNull
  @Pattern(
      regexp = "WORKING|ON_LEAVE|RESIGNED",
      message = "Invalid employmentStatus value should is (WORKING|ON_LEAVE|RESIGNED)")
  String employmentStatus;

  @NotNull
  @Pattern(regexp = "ACTIVE|LOCKED", message = "Invalid banned value should is (ACTIVE|LOCKED)")
  String banned;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "INVALID_PHONE_FORMAT")
  String phone;
}
