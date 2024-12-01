package com.tsoftware.qtd.dto.credit;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class LoanRecordRelateDto {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private String link;
  private String name;
}
