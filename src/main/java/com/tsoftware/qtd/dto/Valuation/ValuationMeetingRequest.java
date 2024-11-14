package com.tsoftware.qtd.dto.Valuation;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class ValuationMeetingRequest {
  private String address;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private String note;
}
