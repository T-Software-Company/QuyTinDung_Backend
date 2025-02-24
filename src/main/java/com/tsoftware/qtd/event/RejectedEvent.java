package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RejectedEvent extends ApplicationEvent {
  private final ApprovalProcessDTO approvalProcessDTO;

  public RejectedEvent(Object source, ApprovalProcessDTO approvalProcessDTO) {
    super(source);
    this.approvalProcessDTO = approvalProcessDTO;
  }
}
