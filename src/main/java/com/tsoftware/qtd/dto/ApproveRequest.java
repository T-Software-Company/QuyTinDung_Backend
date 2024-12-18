package com.tsoftware.qtd.dto;

import com.tsoftware.commonlib.model.AbstractWorkflowRequest;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest extends AbstractWorkflowRequest {
  private UUID transactionId;
  private ApproveStatus status;
}
