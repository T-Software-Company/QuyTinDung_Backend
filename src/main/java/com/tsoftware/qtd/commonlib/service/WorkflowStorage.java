package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.List;
import java.util.UUID;

public interface WorkflowStorage<T extends Workflow> {
  List<T> getByStatus(UUID targetUUID, WorkflowStatus status);

  T save(T workflow);
}
