package com.tsoftware.qtd.dto.approval;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupApprovalRequest {
  private UUID id;
}
