package com.tsoftware.qtd.event;

import java.util.UUID;
import lombok.Getter;

@Getter
public class LoanPlanSubmittedEvent extends SubmittedEvent {

  public LoanPlanSubmittedEvent(Object source, UUID approvalProcessId) {
    super(source, approvalProcessId);
  }
}
