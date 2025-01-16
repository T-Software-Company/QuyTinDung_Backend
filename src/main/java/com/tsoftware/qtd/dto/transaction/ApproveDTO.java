package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveDTO {
  private UUID id;
  private String comment;
  private EmployeeResponse approver;
  private ApproveStatus status;
  private WorkflowTransactionRequest transaction;
  private GroupApproveRequest groupApprove;
  private RoleApproveRequest roleApprove;
}
