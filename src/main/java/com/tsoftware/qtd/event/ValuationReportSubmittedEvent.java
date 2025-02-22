package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import lombok.Getter;

@Getter
public class ValuationReportSubmittedEvent extends SubmittedEvent {

  public ValuationReportSubmittedEvent(Object source, ApprovalProcessResponse approvalProcess) {
    super(source, approvalProcess);
  }
}
