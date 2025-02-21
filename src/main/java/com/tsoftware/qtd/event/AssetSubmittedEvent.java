package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;

public class AssetSubmittedEvent extends SubmittedEvent {
  public AssetSubmittedEvent(Object object, ApprovalProcessResponse approvalProcessResponse) {
    super(object, approvalProcessResponse);
  }
}
