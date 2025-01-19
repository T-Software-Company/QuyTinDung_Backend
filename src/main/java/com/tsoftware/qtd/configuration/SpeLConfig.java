package com.tsoftware.qtd.configuration;

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
      try {
        var response = JsonParser.convert(h.get("response"), WorkflowTransactionResponse.class);
        if (response.getStatus().equals(ActionStatus.APPROVED)) return WorkflowStatus.COMPLETED;
      } catch (Exception e) {
        //
      }
    }
    try {
      var history = JsonParser.convert(histories.getLast(), Map.class);
      var response = JsonParser.convert(history.get("response"), WorkflowTransactionResponse.class);
      if (response.getStatus().equals(ActionStatus.REJECTED)) return WorkflowStatus.DENIED;
    } catch (Exception e) {
      log.error("Error extracting status: {}", e.getMessage());
      return WorkflowStatus.INPROGRESS;
    }
    return WorkflowStatus.INPROGRESS;
  }

  public boolean resolveAddAssetCollateralStep(StepHistoryDTO stepHistory) {
    int validationPrice = 500000;
    try {
      var context = JsonPath.parse(stepHistory.getMetadata());
      var loanRequestResponse =
          context.read(
              "$.histories[-1].response.workflowTransactionResponse.metadata",
              LoanRequestResponse.class);
      return loanRequestResponse.getAmount().compareTo(BigDecimal.valueOf(validationPrice)) >= 0;
    } catch (Exception e) {
      log.error("Error resolving step: {}", e.getMessage());
      return false;
    }
  }
}
