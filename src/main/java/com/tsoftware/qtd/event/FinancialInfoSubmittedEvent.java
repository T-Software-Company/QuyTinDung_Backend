package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;

@Getter
public class FinancialInfoSubmittedEvent extends SubmittedEvent {

  public FinancialInfoSubmittedEvent(
      Object source, ApprovalProcessResponse approvalProcessResponse) {
    super(source, approvalProcessResponse);
  }
}
