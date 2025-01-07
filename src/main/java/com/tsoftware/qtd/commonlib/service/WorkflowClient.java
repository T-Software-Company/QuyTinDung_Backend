package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.Workflow;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface WorkflowClient<T extends Workflow> {

  @GetMapping(value = "/workflows")
  ResponseEntity<List<T>> getByStatus(
      @RequestParam UUID targetId, @RequestParam WorkflowStatus status);

  @PostMapping(value = "/workflows")
  ResponseEntity<T> createOrUpdate(@RequestBody T workflow);
}
