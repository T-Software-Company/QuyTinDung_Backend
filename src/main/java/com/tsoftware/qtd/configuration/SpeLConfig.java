package com.tsoftware.qtd.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.commonlib.util.StringUtils;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.application.LoanPlanResponse;
import com.tsoftware.qtd.dto.application.LoanRequestResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessResponse;
import com.tsoftware.qtd.dto.workflow.StepHistoryDTO;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SpeLConfig {

  public WorkflowStatus extractStatus(StepHistoryDTO stepHistory) throws JsonProcessingException {
    var status = WorkflowStatus.INPROGRESS;
    var histories =
        JsonPath.parse(stepHistory.getMetadata()).read("histories[?(@.response)]", List.class);
    if (histories.isEmpty()) {
      return WorkflowStatus.INPROGRESS;
    }
    var last = histories.getLast();
    var approvalProcess =
        JsonParser.getValueByPath(
            last,
            "response."
                + StringUtils.lowercaseFirstLetter(ApprovalProcessResponse.class.getSimpleName()),
            ApprovalProcessResponse.class);
    if (approvalProcess.getStatus().equals(ActionStatus.APPROVED)) return WorkflowStatus.COMPLETED;
    if (approvalProcess.getStatus().equals(ActionStatus.REJECTED)) return WorkflowStatus.DENIED;
    return WorkflowStatus.INPROGRESS;
  }

  public boolean resolveAddAssetCollateralStep(StepHistoryDTO stepHistory)
      throws JsonProcessingException {
    int validationPrice = 500000;
    var histories =
        JsonPath.parse(stepHistory.getMetadata()).read("histories[?(@.response)]", List.class);
    if (histories.isEmpty()) {
      return false;
    }
    var last = histories.getLast();
    var approvalProcess =
        JsonParser.getValueByPath(
            last,
            "response."
                + StringUtils.lowercaseFirstLetter(ApprovalProcessResponse.class.getSimpleName()),
            ApprovalProcessResponse.class);
    var type = approvalProcess.getType();
    if (ProcessType.CREATE_LOAN_PLAN.equals(type)) {
      var loanPlanResponse =
          JsonParser.convert(approvalProcess.getMetadata(), LoanPlanResponse.class);
      return loanPlanResponse.getProposedLoanAmount().compareTo(BigDecimal.valueOf(validationPrice))
          >= 0;
    }
    if (ProcessType.CREATE_LOAN_REQUEST.equals(type)) {
      var loanRequestResponse =
          JsonParser.convert(approvalProcess.getMetadata(), LoanRequestResponse.class);

      return loanRequestResponse.getAmount().compareTo(BigDecimal.valueOf(validationPrice)) >= 0;
    }
    throw new CommonException(
        ErrorType.UNEXPECTED, "Type not matched on resolve add asset collateral step");
  }
}
