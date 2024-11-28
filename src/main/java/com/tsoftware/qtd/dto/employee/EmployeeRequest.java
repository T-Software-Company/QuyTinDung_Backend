package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.validation.IsEnum;
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

  @IsEnum(enumClass = Role.class)
  List<String> roles;

  @NotNull @Past ZonedDateTime dayOfBirth;

  @IsEnum(enumClass = Gender.class)
  @NotNull
  String gender;

  @IsEnum(enumClass = EmploymentStatus.class)
  @NotNull
  String status;

  @NotNull Banned banned;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;
}
