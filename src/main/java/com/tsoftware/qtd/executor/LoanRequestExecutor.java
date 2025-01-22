package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.service.ApprovalProcessService;
import com.tsoftware.qtd.service.LoanRequestService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanRequestExecutor")
@RequiredArgsConstructor
public class LoanRequestExecutor extends BaseTransactionExecutor<ApprovalProcessDTO> {
  final LoanRequestMapper loanRequestMapper;
  private final LoanRequestService loanRequestService;
  final ApprovalProcessService approvalProcessService;

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
    log.info("All approvals received for workflowTransaction: {}", approvalProcessDTO.getId());
    var request = JsonParser.convert(approvalProcessDTO.getMetadata(), LoanRequestRequest.class);
    var result =
        loanRequestService.create(request, UUID.fromString(request.getApplication().getId()));
    approvalProcessDTO.setReferenceId(result.getId());
  }

  @Override
  protected ApprovalProcessDTO postExecute(ApprovalProcessDTO approvalProcessDTO) {
    return approvalProcessService.updateTransaction(approvalProcessDTO);
  }
}
