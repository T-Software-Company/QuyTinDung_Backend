package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.dto.application.FinancialInfoDTO;
import com.tsoftware.qtd.dto.application.LoanPlanRequest;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import com.tsoftware.qtd.dto.approval.ApprovalProcessDTO;
import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.dto.loan.RepaymentScheduleDTO;
import com.tsoftware.qtd.entity.*;
import com.tsoftware.qtd.entity.ApprovalProcess;
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

  ApprovalProcess toEntity(ApprovalProcessDTO approvalProcessDTO);

  ApprovalProcessDTO toDTO(ApprovalProcess approvalProcess);

  Document toEntity(DocumentDTO documentDTO);

  DocumentDTO toDTO(Document document);

  @Mapping(target = "approvals", ignore = true)
  void updateEntity(@MappingTarget ApprovalProcess entity, ApprovalProcessDTO approvalProcessDTO);

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
