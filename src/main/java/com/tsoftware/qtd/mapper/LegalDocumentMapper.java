package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.entity.LegalDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LegalDocumentMapper {
  LegalDocument toEntity(LegalDocumentResponse dto);

  LegalDocumentResponse toDto(LegalDocument entity);

  void updateEntity(LegalDocumentResponse dto, @MappingTarget LegalDocument entity);
}
