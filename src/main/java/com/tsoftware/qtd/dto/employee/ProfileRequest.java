package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
public class ProfileRequest {

  @NotBlank(message = "EMAIL_REQUIRED")
  @Email(message = "INVALID_EMAIL_FORMAT")
  String email;

  @Valid AddressDto address;

  @NotBlank(message = "FIRST_NAME_REQUIRED")
  String firstName;

  @NotBlank(message = "LAST_NAME_REQUIRED")
  String lastName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Past(message = "DOB_MUST_BE_IN_PAST")
  LocalDate setDayOfBirth;

  @NotBlank(message = "PHONE_REQUIRED")
  @Size(max = 15, message = "PHONE_TOO_LONG")
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "INVALID_PHONE_FORMAT")
  String phone;
}
