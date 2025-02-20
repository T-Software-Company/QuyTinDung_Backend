package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.CollectionUtils;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.FinancialInfoRequest;
import com.tsoftware.qtd.dto.application.FinancialInfoResponse;
import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.dto.approval.ApprovalRequest;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.service.ApprovalProcessService;
import com.tsoftware.qtd.service.FinancialInfoService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialInfoExecutor extends BaseTransactionExecutor<ApprovalProcessDTO> {
  private final ApprovalProcessService approvalProcessService;
  private final FinancialInfoService financialInfoService;

  @Override
  protected void preValidate(ApprovalProcessDTO approvalProcessDTO) {
    approvalProcessService.validateApprovalProcess(approvalProcessDTO);
  }

  @Override
  protected void processApproval(ApprovalProcessDTO approvalProcessDTO, Object... args) {
    var approvalRequest =
        CollectionUtils.findFirst(Arrays.stream(args).toList(), a -> a instanceof ApprovalRequest)
            .orElseThrow(() -> new CommonException(ErrorType.MISSING_REQUIRED_ARGUMENT));
    approvalProcessService.processApproval(approvalProcessDTO, (ApprovalRequest) approvalRequest);
  }

  @Override
  protected void doExecute(ApprovalProcessDTO approvalProcessDTO) {
    log.info("All approvals received for approvalProcessDTO: {}", approvalProcessDTO.getId());
    var data = JsonParser.convert(approvalProcessDTO.getMetadata(), FinancialInfoRequest.class);
    FinancialInfoResponse result = financialInfoService.create(data);
    approvalProcessDTO.setReferenceIds(List.of(result.getId()));
  }

  @Override
  protected ApprovalProcessDTO postExecute(ApprovalProcessDTO approvalProcessDTO) {
    return approvalProcessService.update(approvalProcessDTO);
  }
}
