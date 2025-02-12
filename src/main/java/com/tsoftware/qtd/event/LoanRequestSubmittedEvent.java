package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoanRequestSubmittedEvent extends ApplicationEvent {
  private final ApprovalProcessResponse approvalProcessResponse;

  public LoanRequestSubmittedEvent(Object source, ApprovalProcessResponse response) {
    super(source);
    this.approvalProcessResponse = response;
  }
}
