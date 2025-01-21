package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest {
  private UUID id;
  private ActionStatus status;
}
