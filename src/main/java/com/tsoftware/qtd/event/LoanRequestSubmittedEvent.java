package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;

@Getter
public class LoanRequestSubmittedEvent extends SubmittedEvent {

  public LoanRequestSubmittedEvent(Object source, ApprovalProcessResponse approvalProcessResponse) {
    super(source, approvalProcessResponse);
  }
}
