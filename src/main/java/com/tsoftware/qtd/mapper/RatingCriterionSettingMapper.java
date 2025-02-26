package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.setting.RatingCriterionSettingRequest;
import com.tsoftware.qtd.dto.setting.RatingCriterionSettingResponse;
import com.tsoftware.qtd.entity.RatingCriterionSetting;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RatingCriterionSettingMapper {
  RatingCriterionSetting toEntity(RatingCriterionSettingRequest request);

  RatingCriterionSettingResponse toResponse(RatingCriterionSetting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(
      RatingCriterionSettingRequest request, @MappingTarget RatingCriterionSetting entity);
}
