package com.tsoftware.qtd.event;

import java.util.UUID;
import lombok.Getter;

@Getter
public class FinancialInfoSubmittedEvent extends SubmittedEvent {

  public FinancialInfoSubmittedEvent(Object source, UUID approvalProcessId) {
    super(source, approvalProcessId);
  }
}
