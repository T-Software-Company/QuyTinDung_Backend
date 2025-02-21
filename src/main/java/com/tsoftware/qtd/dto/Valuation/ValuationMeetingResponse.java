package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ValuationMeetingResponse extends AbstractResponse {

  private String address;
  private String note;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private List<EmployeeResponse> participants;
  private Map<String, Object> metadata;
}
