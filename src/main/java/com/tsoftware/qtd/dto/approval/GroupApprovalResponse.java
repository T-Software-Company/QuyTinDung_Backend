package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import java.util.List;
import java.util.UUID;
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
public class GroupApprovalResponse {
  private UUID groupId;
  private Integer requiredPercentage;
  private List<ApprovalResponse> currentApproves;
  private ActionStatus status;
}
