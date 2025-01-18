package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.CreditRatingDTO;
import com.tsoftware.qtd.entity.CreditRating;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CreditRatingMapper {
  CreditRating toEntity(CreditRatingDTO dto);

  CreditRatingDTO toDTO(CreditRating entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CreditRatingDTO dto, @MappingTarget CreditRating entity);
}
