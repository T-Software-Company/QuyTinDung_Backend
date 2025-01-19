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
        var response = JsonParser.convert(h.get("response"), Map.class);
        if (response == null) return WorkflowStatus.INPROGRESS;
        var transaction =
            JsonParser.convert(
                response.get("workflowTransactionResponse"), WorkflowTransactionResponse.class);
        if (transaction.getStatus().equals(ActionStatus.APPROVED)) return WorkflowStatus.COMPLETED;
        if (transaction.getStatus().equals(ActionStatus.REJECTED)) return WorkflowStatus.DENIED;
      } catch (Exception e) {
        log.error("Error extracting status : {}", e.getMessage());
      }
    }
    return WorkflowStatus.INPROGRESS;
  }

  public boolean resolveAddAssetCollateralStep(StepHistoryDTO stepHistory) {
    int validationPrice = 500000;
    try {
      var responses = JsonPath.parse(stepHistory.getMetadata()).read("histories[?(@.response)]");
      var metadataList =
          JsonPath.parse(responses)
              .read("[-1:].response.workflowTransactionResponse.metadata", List.class);
      var loanRequestResponse =
          JsonParser.convert(metadataList.getFirst(), LoanRequestResponse.class);
      return loanRequestResponse.getAmount().compareTo(BigDecimal.valueOf(validationPrice)) >= 0;
    } catch (Exception e) {
      log.error("Error resolving step: {}", e.getMessage());
      return false;
    }
  }
}
