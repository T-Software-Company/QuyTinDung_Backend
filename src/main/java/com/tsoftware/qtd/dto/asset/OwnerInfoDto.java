package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class OwnerInfoDto {

  private String fullName;
  private ZonedDateTime dayOfBirth;
  private String idCardNumber;
  private String permanentAddress;
}
