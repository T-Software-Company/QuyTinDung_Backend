package com.tsoftware.qtd.mapper;

import com.tsoftware.commonlib.model.OnboardingWorkflow;
import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.dto.application.LoanPlanDTO;
import com.tsoftware.qtd.dto.application.LoanRequestDTO;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.dto.loan.RepaymentScheduleDTO;
import com.tsoftware.qtd.dto.transaction.Transaction;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.entity.Document;
import com.tsoftware.qtd.entity.FinancialInfo;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.entity.RepaymentSchedule;
import com.tsoftware.qtd.entity.TransactionEntity;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface DtoMapper {

  TransactionEntity toEntity(Transaction transaction);

  Transaction toDomain(TransactionEntity transactionEntity);

  @Mapping(target = "statusUpdatedTime", source = "updatedAt")
  OnboardingWorkflow toWorkflow(OnboardingWorkflowEntity workflow);

  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget OnboardingWorkflowEntity entity, OnboardingWorkflow workflow);

  Document toEntity(DocumentDTO documentDTO);

  DocumentDTO toDto(Document document);

  @Mapping(target = "approves", ignore = true)
  void updateEntity(@MappingTarget TransactionEntity entity, Transaction transaction);

  FinancialInfo toEntity(FinancialInfoDTO request);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget FinancialInfo entity, FinancialInfoDTO financialInfoDTO);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget LoanPlan entity, LoanPlanDTO loanPlanDTO);

  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget LoanRequest entity, LoanRequestDTO loanRequestDTO);

  List<DisbursementDTO> toListDisbursement(List<Disbursement> disbursements);

  List<RepaymentScheduleDTO> toListRepaymentSchedule(List<RepaymentSchedule> repaymentSchedule);

  @Mapping(target = "loanAccountId", source = "loanAccount.id")
  RepaymentScheduleDTO toDomain(RepaymentSchedule repaymentSchedule);
}
