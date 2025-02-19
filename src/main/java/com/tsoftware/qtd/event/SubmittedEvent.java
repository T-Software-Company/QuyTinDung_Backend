package com.tsoftware.qtd.event;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubmittedEvent extends ApplicationEvent {
  private final UUID approvalProcessId;

  public SubmittedEvent(Object source, UUID approvalProcessId) {
    super(source);
    this.approvalProcessId = approvalProcessId;
  }
}
