package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.entity.LegalDocument;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LegalDocumentMapper {
  LegalDocument toEntity(LegalDocumentResponse dto);

  LegalDocumentResponse toDTO(LegalDocument entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(LegalDocumentResponse dto, @MappingTarget LegalDocument entity);
}
