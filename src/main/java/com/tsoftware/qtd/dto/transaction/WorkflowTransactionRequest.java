package com.tsoftware.qtd.dto.transaction;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WorkflowTransactionRequest {
  private UUID id;
}
