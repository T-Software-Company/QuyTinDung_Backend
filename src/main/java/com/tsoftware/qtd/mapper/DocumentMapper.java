package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface DocumentMapper {
  Document toEntity(DocumentDTO documentDTO);

  DocumentDTO toDTO(Document document);
}
