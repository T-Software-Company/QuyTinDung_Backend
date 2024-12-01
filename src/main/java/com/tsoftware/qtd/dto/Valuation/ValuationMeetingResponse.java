package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValuationMeetingResponse {
  private String id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private String address;
  private String note;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private List<EmployeeResponse> participants;
}
