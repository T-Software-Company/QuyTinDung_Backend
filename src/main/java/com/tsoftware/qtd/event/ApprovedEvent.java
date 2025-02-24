package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ApprovedEvent extends ApplicationEvent {
  private final ApprovalProcessDTO approvalProcessDTO;

  public ApprovedEvent(Object source, ApprovalProcessDTO approvalProcessDTO) {
    super(source);
    this.approvalProcessDTO = approvalProcessDTO;
  }
}
