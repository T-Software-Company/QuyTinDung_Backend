package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.validation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Unique(
    repositoryClass = CustomerRepository.class,
    fields = {"username"})
public class CustomerRequest {
  UUID id;

  @NotNull
  @NotBlank
  @Size(min = 4)
  String username;

  @NotNull
  @NotBlank
  @Size(min = 6)
  String password;

  @NotNull @NotBlank @Email String email;
  String code;

  @NotNull
  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  String phone;

  Boolean enabled;
  @NotNull @NotBlank String firstName;
  @NotNull @NotBlank String lastName;
  @Valid AddressDto address;
  @Valid IdentityInfoDTO identityInfo;
  @NotNull @NotBlank String signaturePhoto;
}
