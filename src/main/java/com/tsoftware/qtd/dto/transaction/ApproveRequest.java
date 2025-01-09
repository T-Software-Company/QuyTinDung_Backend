package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest {
  private UUID id;
  private UUID transactionId;
  private EmployeeResponse approver;
  private ApproveStatus status;
}
