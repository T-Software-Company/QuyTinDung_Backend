package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tsoftware.commonlib.model.AbstractWorkflowResponse;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse extends AbstractWorkflowResponse {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  String fullName;
  String email;
  String phone;
  String note;
  String signaturePhoto;
  Gender gender;
  IdentityInfoDTO identityInfo;
  AddressDto address;
  String status;
  FinancialInfoDTO financialInfo;
}
