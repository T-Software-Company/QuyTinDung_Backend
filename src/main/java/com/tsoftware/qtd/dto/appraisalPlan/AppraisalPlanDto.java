package com.tsoftware.qtd.dto.appraisalPlan;

import com.tsoftware.qtd.entity.*;
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
public class AppraisalPlanDto {
  private String address;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
}
