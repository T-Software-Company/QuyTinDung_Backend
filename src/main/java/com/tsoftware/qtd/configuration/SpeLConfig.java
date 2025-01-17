package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import com.tsoftware.qtd.dto.workflow.StepHistoryDTO;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeLConfig {

  public WorkflowStatus extractStatus(StepHistoryDTO stepHistory) {
    var metadata = stepHistory.getMetadata();
    var histories = JsonParser.convert(metadata.get("histories"), List.class);
    var history = JsonParser.convert(histories.getLast(), Map.class);
    var responseObject = history.get("response");
    if (responseObject == null) return WorkflowStatus.INPROGRESS;
    try {
      var response = JsonParser.convert(responseObject, WorkflowTransactionResponse.class);
      if (response.getStatus().equals(ApproveStatus.APPROVED)) return WorkflowStatus.COMPLETED;
      if (response.getStatus().equals(ApproveStatus.REJECTED)) return WorkflowStatus.DENIED;
    } catch (Exception e) {
      return WorkflowStatus.INPROGRESS;
    }
    return WorkflowStatus.INPROGRESS;
  }
}
