package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.dto.application.FinancialInfoDTO;
import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.dto.loan.RepaymentScheduleDTO;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionDTO;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.entity.Document;
import com.tsoftware.qtd.entity.FinancialInfo;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.entity.RepaymentSchedule;
import com.tsoftware.qtd.entity.WorkflowTransaction;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface DTOMapper {

  WorkflowTransaction toEntity(WorkflowTransactionDTO workflowTransactionDTO);

  WorkflowTransactionDTO toDTO(WorkflowTransaction workflowTransaction);

  Document toEntity(DocumentDTO documentDTO);

  DocumentDTO toDTO(Document document);

  @Mapping(target = "approves", ignore = true)
  void updateEntity(
      @MappingTarget WorkflowTransaction entity, WorkflowTransactionDTO workflowTransactionDTO);

  FinancialInfo toEntity(FinancialInfoDTO request);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget FinancialInfo entity, FinancialInfoDTO financialInfoDTO);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget LoanPlan entity, LoanPlanRequest loanPlanRequest);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget LoanRequest entity, LoanRequestRequest loanRequestRequest);

  List<DisbursementDTO> toListDisbursement(List<Disbursement> disbursements);

  List<RepaymentScheduleDTO> toListRepaymentSchedule(List<RepaymentSchedule> repaymentSchedule);

  @Mapping(target = "loanAccountId", source = "loanAccount.id")
  RepaymentScheduleDTO toDomain(RepaymentSchedule repaymentSchedule);
}
