package com.tsoftware.qtd.service;

import com.tsoftware.qtd.commonlib.executor.TransactionExecutorRegistry;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import com.tsoftware.qtd.dto.transaction.ApproveRequest;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.dto.transaction.TransactionDTO;
import com.tsoftware.qtd.entity.TransactionEntity;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.TransactionRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
  final TransactionExecutorRegistry registry;
  final TransactionRepository repository;
  final DtoMapper mapper;

  public Object approve(ApproveRequest approveRequest) {
    var transactionDTO =
        repository
            .findById(approveRequest.getTransactionId())
            .map(mapper::toDTO)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, approveRequest.getTransactionId()));
    if (ApproveStatus.REJECTED.equals(approveRequest.getStatus())) {
      transactionDTO.setStatus(ApproveStatus.REJECTED);
      repository.save(mapper.toEntity(transactionDTO));
      ApproveResponse response = new ApproveResponse();
      //      response.setData(
      //          ApproveDTO.builder()
      //              .transactionId(transactionDTO.getId())
      //              .status(ApproveStatus.REJECTED)
      //              .build());
      return response;
    }
    return registry.getExecutor(transactionDTO.getType()).execute(transactionDTO);
  }

  public void updateTransaction(TransactionDTO transactionDTO) {
    TransactionEntity entity =
        repository
            .findById(transactionDTO.getId())
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, transactionDTO.getId()));
    mapper.updateEntity(entity, transactionDTO);

    List<ApproveDTO> approveDTOS = transactionDTO.getApproves();
    entity
        .getApproves()
        .forEach(
            approve ->
                approveDTOS.stream()
                    .filter(
                        dto ->
                            dto.getApprover().getUserId().equals(approve.getApprover().getUserId()))
                    .findFirst()
                    .ifPresent(dto -> approve.setStatus(dto.getStatus())));

    if (transactionDTO.isApproved()) {
      entity.setStatus(ApproveStatus.APPROVED);
      entity.setApprovedAt(ZonedDateTime.now());
    }
    repository.save(entity);
  }

  public void validateTransaction(TransactionDTO transactionDTO) {
    var userId = RequestUtil.getUserId();
    boolean hasPermission =
        transactionDTO.getApproves().stream()
            .anyMatch(
                approve -> {
                  var employee = approve.getApprover();
                  return userId.equals(employee.getUserId());
                });
    if (!hasPermission) {
      throw new CommonException(
          ErrorType.ACCESS_DENIED, "You don't have permission to approve this transactionDTO.");
    }

    if (transactionDTO.isApproved()) {
      throw new CommonException(
          ErrorType.DUPLICATED_REQUEST, "TransactionDTO is already approved.");
    }
  }

  public TransactionDTO processApproval(TransactionDTO transactionDTO) {
    var userId = RequestUtil.getUserId();
    var approvers = Optional.ofNullable(transactionDTO.getApproves()).orElse(new ArrayList<>());
    approvers.stream()
        .filter(approve -> userId.equals(approve.getApprover().getUserId()))
        .forEach(approve -> approve.setStatus(ApproveStatus.APPROVED));
    return transactionDTO;
  }
}
