package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.validation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRequest {
  UUID id;

  @NotBlank
  @Size(min = 4)
  @Unique(repositoryClass = CustomerRepository.class, checkMethod = "existsByUsername")
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
  @Valid AddressDto address;
  @Valid IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
