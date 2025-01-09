package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproveResponse extends AbstractResponse {
  private UUID id;
  private UUID transactionId;
  private EmployeeResponse approver;
  private ApproveStatus status;
}
