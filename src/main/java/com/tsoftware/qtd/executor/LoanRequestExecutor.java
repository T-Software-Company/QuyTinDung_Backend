package com.tsoftware.qtd.executor;

import com.tsoftware.qtd.commonlib.context.WorkflowContext;
import com.tsoftware.qtd.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.TransactionDTO;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanRequestExecutor")
@RequiredArgsConstructor
public class LoanRequestExecutor extends BaseTransactionExecutor<TransactionDTO> {
  final DtoMapper dtoMapper;
  final LoanRequestMapper loanRequestMapper;
  final ApplicationRepository applicationRepository;
  final ApplicationService applicationService;
  final TransactionService transactionService;

  @Override
  protected void preValidate(TransactionDTO transactionDTO) {
    transactionService.validateTransaction(transactionDTO);
  }

  @Override
  protected TransactionDTO processApproval(TransactionDTO transactionDTO) {
    return transactionService.processApproval(transactionDTO);
  }

  @Override
  protected Object doExecute(TransactionDTO transactionDTO) {
    log.info("All approvals received for transactionDTO: {}", transactionDTO.getId());
    var request = JsonParser.convert(transactionDTO.getMetadata(), LoanRequestDTO.class);
    applicationService.createOrUpdateLoanRequest(transactionDTO.getApplication().getId(), request);
    ApproveResponse response = new ApproveResponse();
    //    response.setData(
    //        ApproveDTO.builder()
    //            .transactionId(transactionDTO.getId())
    //            .status(ApproveStatus.APPROVED)
    //            .build());
    return response;
  }

  @Override
  protected void postExecute(TransactionDTO transactionDTO) {
    transactionService.updateTransaction(transactionDTO);
    WorkflowContext.putMetadata(transactionDTO.getId().toString(), transactionDTO.getStatus());
  }
}
