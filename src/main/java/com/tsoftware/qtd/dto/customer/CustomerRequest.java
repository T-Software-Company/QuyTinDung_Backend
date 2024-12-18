package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsoftware.commonlib.model.AbstractWorkflowRequest;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class CustomerRequest extends AbstractWorkflowRequest {
  @NotNull String fullName;
  @Email String email;
  String phone;
  String note;
  Gender gender;
  String status;
  @NotNull IdentityInfoDTO identityInfo;
  AddressDto address;
  FinancialInfoDTO financialInfo;
  String signaturePhoto;
}
