package com.tsoftware.qtd.dto;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveResponse {
  private EmployeeResponse approver;
  private ApproveStatus status;
}
