package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MarketStallsRequest;
import com.tsoftware.qtd.entity.MarketStalls;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MarketStallsMapper {
  MarketStalls toEntity(MarketStallsRequest dto);

  MarketStallsRequest toDTO(MarketStalls entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(MarketStallsRequest dto, @MappingTarget MarketStalls entity);
}
