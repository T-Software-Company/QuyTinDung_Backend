package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.dto.employee.EmployeeSimpleResponse;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalResponse {
  private UUID id;
  private String comment;
  private ActionStatus status;
  private EmployeeSimpleResponse approver;
}
