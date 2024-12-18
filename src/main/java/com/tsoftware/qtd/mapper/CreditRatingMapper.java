package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.CreditRatingDto;
import com.tsoftware.qtd.entity.CreditRating;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CreditRatingMapper {
  CreditRating toEntity(CreditRatingDto dto);

  CreditRatingDto toDto(CreditRating entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CreditRatingDto dto, @MappingTarget CreditRating entity);
}
