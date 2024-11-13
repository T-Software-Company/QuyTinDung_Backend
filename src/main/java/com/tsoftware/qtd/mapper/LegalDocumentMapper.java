package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.LegalDocumentDto;
import com.tsoftware.qtd.entity.LegalDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LegalDocumentMapper {
  LegalDocument toEntity(LegalDocumentDto dto);

  LegalDocumentDto toDto(LegalDocument entity);

  void updateEntity(LegalDocumentDto dto, @MappingTarget LegalDocument entity);
}
