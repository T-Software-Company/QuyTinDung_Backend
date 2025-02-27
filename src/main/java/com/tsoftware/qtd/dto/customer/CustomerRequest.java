package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.validation.IsUUID;
import com.tsoftware.qtd.validation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
  @IsUUID private String id;
  private String avatarUrl;

  @NotNull
  @NotBlank
  @Size(min = 4)
  private String username;

  @NotNull
  @NotBlank
  @Size(min = 6)
  private String password;

  @NotNull @NotBlank @Email private String email;
  private String code;

  @NotNull
  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  private String phone;

  private Boolean enabled;
  @NotNull @NotBlank private String firstName;
  @NotNull @NotBlank private String lastName;
  @Valid @NotNull private AddressDTO address;
  @Valid @NotNull private IdentityInfoDTO identityInfo;
  @NotNull @NotBlank private String signaturePhoto;
}
