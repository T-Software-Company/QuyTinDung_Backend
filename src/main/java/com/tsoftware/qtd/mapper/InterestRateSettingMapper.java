package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.setting.InterestRateSettingDTO;
import com.tsoftware.qtd.dto.setting.InterestRateSettingRequest;
import com.tsoftware.qtd.dto.setting.InterestRateSettingResponse;
import com.tsoftware.qtd.entity.InterestRateSetting;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InterestRateSettingMapper {
  InterestRateSetting toEntity(InterestRateSettingDTO DTO);

  InterestRateSetting toEntity(InterestRateSettingRequest request);

  InterestRateSettingDTO toDTO(InterestRateSetting entity);

  InterestRateSettingResponse toResponse(InterestRateSetting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(InterestRateSettingDTO DTO, @MappingTarget InterestRateSetting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(InterestRateSettingRequest request, @MappingTarget InterestRateSetting entity);
}
