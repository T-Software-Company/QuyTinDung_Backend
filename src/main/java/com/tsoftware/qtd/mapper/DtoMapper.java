package com.tsoftware.qtd.mapper;

import com.tsoftware.commonlib.model.OnboardingWorkflow;
import com.tsoftware.qtd.dto.Transaction;
import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.entity.Document;
import com.tsoftware.qtd.entity.OnboardingWorkflowEntity;
import com.tsoftware.qtd.entity.TransactionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DtoMapper {

  TransactionEntity toEntity(Transaction transaction);

  @Mapping(target = "applicationId", source = "application.id")
  Transaction toDomain(TransactionEntity transactionEntity);

  @Mapping(target = "statusUpdatedTime", source = "updatedAt")
  OnboardingWorkflow toWorkflow(OnboardingWorkflowEntity workflow);

  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget OnboardingWorkflowEntity entity, OnboardingWorkflow workflow);

  Document toEntity(DocumentDTO documentDTO);

  DocumentDTO toDto(Document document);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget TransactionEntity entity, Transaction transaction);
}
