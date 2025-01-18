package com.tsoftware.qtd.configuration;

import com.jayway.jsonpath.JsonPath;
import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
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
    var history = JsonParser.convert(histories.getLast(), Map.class);
    var responseObject = history.get("response");
    if (responseObject == null) return WorkflowStatus.INPROGRESS;
    try {
      var response = JsonParser.convert(responseObject, WorkflowTransactionResponse.class);
      if (response.getStatus().equals(ApproveStatus.APPROVED)) return WorkflowStatus.COMPLETED;
      if (response.getStatus().equals(ApproveStatus.REJECTED)) return WorkflowStatus.DENIED;
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
