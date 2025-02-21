package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubmittedEvent extends ApplicationEvent {
  private final ApprovalProcessResponse approvalProcessResponse;

  public SubmittedEvent(Object source, ApprovalProcessResponse approvalProcessResponse) {
    super(source);
    this.approvalProcessResponse = approvalProcessResponse;
  }
}
