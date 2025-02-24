package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ApprovalSubmittedEvent extends ApplicationEvent {
  private final ApprovalProcessResponse approvalProcessResponse;

  public ApprovalSubmittedEvent(Object source, ApprovalProcessResponse approvalProcessResponse) {
    super(source);
    this.approvalProcessResponse = approvalProcessResponse;
  }
}
