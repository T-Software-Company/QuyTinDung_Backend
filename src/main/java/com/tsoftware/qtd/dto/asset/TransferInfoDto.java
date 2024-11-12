package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class TransferInfoDto {

  private String fullName;
  private ZonedDateTime dayOfBirth;
  private String idCardNumber;
  private String permanentAddress;
  private ZonedDateTime transferDate;
  private String transferRecordNumber;
}
