package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferInfoDTO {
  private UUID id;
  private String fullName;
  private ZonedDateTime dayOfBirth;
  private String idCardNumber;
  private String permanentAddress;
  private ZonedDateTime transferDate;
  private String transferRecordNumber;
}
