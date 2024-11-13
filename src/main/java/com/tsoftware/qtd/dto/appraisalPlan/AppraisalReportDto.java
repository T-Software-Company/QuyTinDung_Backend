package com.tsoftware.qtd.dto.appraisalPlan;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppraisalReportDto {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
}
