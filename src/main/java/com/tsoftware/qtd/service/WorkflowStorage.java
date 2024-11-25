package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import com.tsoftware.qtd.model.Workflow;
import java.util.List;
import java.util.UUID;

public interface WorkflowStorage<T extends Workflow> {
  List<T> getByStatus(UUID targetUUID, WorkflowStatus status);

  T save(T workflow);
}
