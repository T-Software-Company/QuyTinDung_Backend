package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.dto.customer.IdentityInfoDTO;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
@Unique(
    repositoryClass = EmployeeRepository.class,
    fields = {"username"})
public class EmployeeRequest {

  @NotBlank
  @Size(min = 4)
  String username;

  @NotBlank
  @Size(min = 6)
  String password;

  @NotBlank @Email String email;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;

  @NotBlank String firstName;
  @NotBlank String lastName;
  @Valid AddressDTO address;
  @Valid IdentityInfoDTO identityInfo;

  @IsEnum(enumClass = Role.class)
  List<String> roles;

  List<@Valid GroupDTO> groups;
}
