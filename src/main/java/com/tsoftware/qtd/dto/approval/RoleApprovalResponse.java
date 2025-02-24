package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleApprovalResponse {
  private Role role;
  private Integer requiredCount;
  private ApprovalStatus status;
  private ApprovalProcessResponse approvalProcess;
}
