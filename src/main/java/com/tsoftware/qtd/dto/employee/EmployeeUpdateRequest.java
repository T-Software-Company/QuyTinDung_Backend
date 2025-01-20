package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.dto.customer.IdentityInfoDTO;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateRequest {

  @NotBlank
  @Size(min = 4)
  String username;

  @NotBlank @Email String email;

  @Valid @NotNull AddressDTO address;
  @Valid @NotNull IdentityInfoDTO identityInfo;

  @NotBlank String firstName;

  @NotBlank String lastName;

  @IsEnum(enumClass = Role.class)
  List<String> roles;

  List<@Valid GroupDTO> groups;

  @NotNull @NotBlank String code;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;
}
