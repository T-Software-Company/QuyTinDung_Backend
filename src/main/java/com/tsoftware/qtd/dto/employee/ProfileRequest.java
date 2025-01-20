package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.dto.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
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
public class ProfileRequest {

  @NotBlank @Email String email;

  @Valid @NotNull AddressDTO address;

  @NotBlank String firstName;

  @NotBlank String lastName;

  @Past ZonedDateTime setDayOfBirth;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;
}
