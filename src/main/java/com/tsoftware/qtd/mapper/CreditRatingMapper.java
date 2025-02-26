package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.application.CreditRatingRequest;
import com.tsoftware.qtd.dto.application.CreditResponse;
import com.tsoftware.qtd.entity.CreditRating;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CreditRatingMapper {
  CreditRating toEntity(CreditRatingRequest request);

  CreditResponse toResponse(CreditRating entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CreditRatingRequest request, @MappingTarget CreditRating entity);
}
