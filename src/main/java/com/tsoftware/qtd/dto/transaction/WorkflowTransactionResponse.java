package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import java.time.ZonedDateTime;
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
public class WorkflowTransactionResponse extends AbstractResponse {
  private ApproveStatus status;
  private TransactionType type;
  private UUID referenceId;
  private ZonedDateTime approvedAt;
  private Integer requiredApprovals;
  private String PIC;
  private List<ApproveDTO> approves;
  private Object metadata;
  private ApplicationResponse application;
}
