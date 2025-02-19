package com.tsoftware.qtd.event;

import java.util.UUID;
import lombok.Getter;

@Getter
public class LoanRequestSubmittedEvent extends SubmittedEvent {

  public LoanRequestSubmittedEvent(Object source, UUID approvalProcessId) {
    super(source, approvalProcessId);
  }
}
