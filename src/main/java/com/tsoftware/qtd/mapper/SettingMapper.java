package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.setting.ApproveSettingRequest;
import com.tsoftware.qtd.dto.setting.ApproveSettingResponse;
import com.tsoftware.qtd.entity.ApproveSetting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface SettingMapper {

  ApproveSetting toEntity(ApproveSettingRequest approveSettingRequest);

  ApproveSettingResponse toResponse(ApproveSetting saved);

  void updateEntity(
      @MappingTarget ApproveSetting entity, ApproveSettingRequest approveSettingRequest);
}
