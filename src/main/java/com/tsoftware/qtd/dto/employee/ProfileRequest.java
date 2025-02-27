package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.dto.customer.IdentityInfoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

  @NotBlank @Email private String email;

  @Valid @NotNull private AddressDTO address;

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank
  @Size(max = 15)
  @Pattern(regexp = "^[0-9\\-\\+]{9,15}$")
  private String phone;

  private String username;

  @Valid @NotNull private IdentityInfoDTO identityInfo;
}
