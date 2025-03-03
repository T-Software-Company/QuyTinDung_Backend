package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ApprovalUpdatedEvent extends ApplicationEvent {
  private final ApprovalProcessDTO approvalProcessDTO;

  public ApprovalUpdatedEvent(Object o, ApprovalProcessDTO dto) {
    super(o);
    this.approvalProcessDTO = dto;
  }
}
