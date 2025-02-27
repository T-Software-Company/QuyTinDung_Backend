package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDTO;
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
public class CustomerDTO {
  private UUID id;
  private String avatarUrl;
  private String username;
  private String password;
  private String email;
  private String code;
  private String phone;
  private Boolean enabled;
  private String firstName;
  private String lastName;
  private AddressDTO address;
  private IdentityInfoDTO identityInfo;
  private String signaturePhoto;
}
