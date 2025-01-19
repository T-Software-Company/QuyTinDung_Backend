package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import java.util.List;
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
public class RoleApproveResponse {
  private Role role;
  private Integer requiredCount;
  private List<ApproveResponse> currentApproves;
  private ActionStatus status;
}
