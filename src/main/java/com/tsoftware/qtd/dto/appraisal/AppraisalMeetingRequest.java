package com.tsoftware.qtd.dto.appraisal;

import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
public class AppraisalMeetingRequest {
  @NotBlank(message = "Address cannot be blank")
  private String address;

  @NotNull(message = "Start date cannot be null")
  @FutureOrPresent(message = "Start date must be in the present or future")
  private ZonedDateTime startDate;

  @NotNull(message = "End date cannot be null")
  @Future(message = "End date must be in the future")
  private ZonedDateTime endDate;

  private String note;
  private Map<String, Object> metadata;
  private List<Employee> participants;

  @AssertTrue(message = "End date must be after start date")
  private boolean isValidDateRange() {
    if (startDate == null || endDate == null) {
      return true;
    }
    return endDate.isAfter(startDate);
  }

  @Valid
  @NotNull(message = "Application cannot be null")
  private ApplicationRequest application;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Employee {
    @NotNull(message = "Employee ID cannot be null")
    @IsUUID
    private String id;
  }
}
