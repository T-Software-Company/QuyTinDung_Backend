package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.service.ApprovalProcessService;
import com.tsoftware.qtd.service.FinancialInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("financialInfoExecutor")
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<ApprovalProcessDTO> {
  private final ApprovalProcessService approvalProcessService;
  private final FinancialInfoService financialInfoService;

  @Override
  protected void preValidate(ApprovalProcessDTO approvalProcessDTO) {
    approvalProcessService.validateTransaction(approvalProcessDTO);
  }

  @Override
  protected ApprovalProcessDTO processApproval(
      ApprovalProcessDTO approvalProcessDTO, ActionStatus status) {
    return approvalProcessService.processApproval(approvalProcessDTO, status);
  }

  @Override
  protected void doExecute(ApprovalProcessDTO approvalProcessDTO) {
    log.info("All approvals received for approvalProcessDTO: {}", approvalProcessDTO.getId());
    var data = JsonParser.convert(approvalProcessDTO.getMetadata(), FinancialInfoRequest.class);
    FinancialInfoResponse result = financialInfoService.create(data);
    approvalProcessDTO.setReferenceId(result.getId());
  }

  @Override
  protected ApprovalProcessDTO postExecute(ApprovalProcessDTO approvalProcessDTO) {
    return approvalProcessService.updateTransaction(approvalProcessDTO);
  }
}
