package com.tsoftware.qtd.dto.appraisalPlan;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppraisalPlanDTO {
  private String address;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
}
