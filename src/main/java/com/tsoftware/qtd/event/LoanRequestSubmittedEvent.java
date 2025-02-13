package com.tsoftware.qtd.event;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoanRequestSubmittedEvent extends ApplicationEvent {
  private final UUID approvalProcessId;

  public LoanRequestSubmittedEvent(Object source, UUID approvalProcessId) {
    super(source);
    this.approvalProcessId = approvalProcessId;
  }
}
