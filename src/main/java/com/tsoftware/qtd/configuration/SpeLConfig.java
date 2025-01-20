package com.tsoftware.qtd.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import com.tsoftware.qtd.dto.workflow.StepHistoryDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SpeLConfig {

  public WorkflowStatus extractStatus(StepHistoryDTO stepHistory) {
    var metadata = stepHistory.getMetadata();
    var histories = JsonParser.convert(metadata.get("histories"), List.class);
    for (Object history : histories) {
      var h = JsonParser.convert(history, Map.class);
      var response = JsonParser.convert(h.get("response"), Map.class);
      if (response == null) return WorkflowStatus.INPROGRESS;
      var transaction =
          JsonParser.convert(
              response.get("workflowTransactionResponse"), WorkflowTransactionResponse.class);
      if (transaction.getStatus().equals(ActionStatus.APPROVED)) return WorkflowStatus.COMPLETED;
      if (transaction.getStatus().equals(ActionStatus.REJECTED)) return WorkflowStatus.DENIED;
    }
    return WorkflowStatus.INPROGRESS;
  }

  public boolean resolveAddAssetCollateralStep(StepHistoryDTO stepHistory)
      throws JsonProcessingException {
    int validationPrice = 500000;
    var histories =
        JsonPath.parse(stepHistory.getMetadata()).read("histories[?(@.response)]", List.class);
    var last = histories.getLast();
    var loanRequestResponse =
        JsonParser.getValueByPath(
            last, "response.workflowTransactionResponse.metadata", LoanRequestResponse.class);
    return loanRequestResponse.getAmount().compareTo(BigDecimal.valueOf(validationPrice)) >= 0;
  }
}
