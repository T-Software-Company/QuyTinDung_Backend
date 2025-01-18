package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MarketStallsDTO;
import com.tsoftware.qtd.entity.MarketStalls;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MarketStallsMapper {
  MarketStalls toEntity(MarketStallsDTO dto);

  MarketStallsDTO toDTO(MarketStalls entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(MarketStallsDTO dto, @MappingTarget MarketStalls entity);
}
