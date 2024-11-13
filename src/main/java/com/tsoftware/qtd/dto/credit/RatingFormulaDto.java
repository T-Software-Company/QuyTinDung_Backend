package com.tsoftware.qtd.dto.credit;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class RatingFormulaDto {
  private String name;
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
}
