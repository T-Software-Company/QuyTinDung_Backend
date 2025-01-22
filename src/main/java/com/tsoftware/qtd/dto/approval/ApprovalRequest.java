package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApprovalRequest {
  private UUID id;
  private ActionStatus status;
}
