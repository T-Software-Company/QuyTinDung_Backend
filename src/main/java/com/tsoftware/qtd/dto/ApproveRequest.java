package com.tsoftware.qtd.dto;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest {
  private EmployeeRequest approver;
  private ApproveStatus status;
}
