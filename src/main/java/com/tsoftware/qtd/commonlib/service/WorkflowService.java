package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.List;
import java.util.UUID;

public interface WorkflowService {
  List<Workflow> getByStatus(UUID targetUUID, WorkflowStatus status);

  Workflow save(Workflow workflow);

  Workflow get(UUID targetId);

  String calculateNextStep(Workflow workflow);

  Workflow init();
}
