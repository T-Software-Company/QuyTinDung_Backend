package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.validation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {
  UUID id;

  @NotBlank
  @Size(min = 4)
  @Unique(repositoryClass = EmployeeRepository.class, checkMethod = "existsByUsername")
  String username;

  @NotBlank
  @Size(min = 6)
  String password;

  @NotBlank @Email String email;
  String code;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;

  Boolean enabled;
  @NotBlank String firstName;
  @NotBlank String lastName;
  @Valid AddressDto address;
  @Valid IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
