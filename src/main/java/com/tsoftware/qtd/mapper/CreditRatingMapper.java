package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.credit.CreditRatingDto;
import com.tsoftware.qtd.entity.CreditRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditRatingMapper {
  CreditRating toEntity(CreditRatingDto dto);

  CreditRatingDto toDto(CreditRating entity);

  void updateEntity(CreditRatingDto dto, @MappingTarget CreditRating entity);
}
