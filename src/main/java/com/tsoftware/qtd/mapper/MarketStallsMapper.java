package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.asset.MarketStallsDto;
import com.tsoftware.qtd.entity.MarketStalls;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarketStallsMapper {
  MarketStalls toEntity(MarketStallsDto dto);

  MarketStallsDto toDto(MarketStalls entity);

  void updateEntity(MarketStallsDto dto, @MappingTarget MarketStalls entity);
}
