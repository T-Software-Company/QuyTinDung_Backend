package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
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
public class ApprovalDTO {
  private UUID id;
  private String comment;
  private EmployeeResponse approver;
  private ProcessType processType;
  private ActionStatus status;
  private ApprovalProcessRequest approvalProcess;
  private GroupApprovalRequest groupApproval;
  private RoleApprovalRequest roleApproval;
}
