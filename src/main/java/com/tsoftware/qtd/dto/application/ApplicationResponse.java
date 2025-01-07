package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.commonlib.model.AbstractWorkflowResponse;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationResponse extends AbstractWorkflowResponse<Object> {
  private UUID applicationId;
  private List<UUID> transactionIds;
}
