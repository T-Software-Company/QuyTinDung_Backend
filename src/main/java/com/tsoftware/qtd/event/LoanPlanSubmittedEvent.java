package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;

@Getter
public class LoanPlanSubmittedEvent extends SubmittedEvent {

  public LoanPlanSubmittedEvent(Object source, ApprovalProcessResponse approvalProcessResponse) {
    super(source, approvalProcessResponse);
  }
}
